import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface TruckListener{
	void notifyOfSensors(Truck truck);
}

public class Receiver implements Runnable, TruckListener{

        private String tableName, query;
        
        private Connection connection;
        
        private Statement statement;
        
        private final String dbURL = "jdbc:derby://localhost:1527/MiningTrucksOneTable";
        
	private final Truck trucks[];
	
	//private final Thread truckThreads[];
	
        private ExecutorService executor;
        
	private HashMap<Integer, HashSet<Integer>> validSensorData;
	
	private int sensorsData[][];
	
        private static int totalPaths ;
        
        private PreparedStatement preparedStatement;
        
        public Receiver(int totalTrucks, int totalTrucksOnPath, int totalSensorsOnATruck) {
		// TODO Auto-generated constructor stub
		
		setValidSensorData(totalSensorsOnATruck);
		
                boolean tableAlreadyExists = false;
                
		try {
                connection  = DriverManager.getConnection(dbURL);
                
                statement = connection.createStatement();
                
                //DatabaseMetaData metaData = connection.getMetaData();
                
                StringBuilder sb;
                
                tableName = "TRUCKSINFO";
                
                //ResultSet rs = metaData.getTables(null, null, tableName, null);
                
                
                //if(!rs.next()){
                        //System.err.println("Table not found");
                        sb = new StringBuilder();
                        sb.append("create table ").append(tableName).append("(Truck_ID INTEGER not NULL, Log LONG VARCHAR not NULL, Current_Path INTEGER not NULL, State VARCHAR(255) not NULL)");
                        
                        query = sb.toString();
                    
                        try{
                            statement.executeUpdate(query);
                        }catch(Exception e){
//                            e.printStackTrace();
//                            System.err.println("Table not created");
                            tableAlreadyExists = true;
                        }
                   // }
                
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
                
                trucks = new Truck[totalTrucks];
		
		sensorsData = new int[totalTrucks][totalSensorsOnATruck];
		
		for(int i = 0, path = 0 ; i < totalTrucks ; ){
			trucks[i] = new Truck(totalSensorsOnATruck, i, path);
			
                        
                        if(!tableAlreadyExists){
                    try {
                        statement.executeUpdate("INSERT INTO TRUCKSINFO (TRUCK_ID, LOG, CURRENT_PATH, STATE) VALUES (" + i + ", '" + new Date(System.currentTimeMillis()) + ": Created', " + path + ", " + "'Working')");
                    } catch (SQLException ex) {
                        Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        }
                        i++;
			if(i % totalTrucksOnPath == 0) path++;
		}
            try {
                preparedStatement = connection.prepareStatement("UPDATE TRUCKSINFO SET LOG = ?, CURRENT_PATH = ?, STATE = ? WHERE TRUCK_ID = ?");
                
                executor = Executors.newFixedThreadPool(totalTrucks + 1);
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int totalTrucksOnPath = 100;
		int sensorsOnATruck = 1000;
		totalPaths = 10;
		
		Receiver receiver = new Receiver(totalTrucksOnPath * totalPaths, totalTrucksOnPath, sensorsOnATruck);
                
                try {
                    Class.forName("org.apache.derby.jdbc.ClientDriver");
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                }
                
		System.out.println("System Inititalized! Begining execution in 3");
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("2");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
                }
		System.out.println("1");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		System.out.println("GO! Time: " + new Time(System.currentTimeMillis()));
		receiver.run();
	}
	
	private void setValidSensorData(int totalSensorsOnTruck){
		validSensorData = new HashMap<>();
		
		HashSet<Integer> validEntries;
		for(int i = 0 ; i < totalSensorsOnTruck ; i++){
			validEntries = new HashSet<>();
			for(int j = 0 ; j < 10000 ; j++) validEntries.add(j);
			validSensorData.put(i, validEntries);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
                for(int i = 0 ; i < trucks.length ; i++){
			trucks[i].addListener(this);
                        executor.execute(trucks[i]);
		}
                
                Scanner sc = new Scanner(System.in);
                displayOptions();
                int opt = sc.nextInt();
                while(true){
                    switch (opt){
                            case 1://Show ID of trucks in critical condition
                                System.out.println("Trucks in critical condition:");
                                for(Truck truck : trucks){
                                    if(truck.getTruckState() == TruckState.Damaged){
                                        System.out.print(truck.getID() + ", ");
                                    }
                                }
                                System.out.println();
                                break;
                            case 2://Show particular truck status
                                System.out.print("Enter truck ID: ");
                                int id = sc.nextInt();
                                if(trucks[id].getTruckState() == TruckState.Damaged){
                                    System.out.println("Truck is in critical condition! Check parts: " + trucks[id].getDamagedPartsIDs());
                                }else if(trucks[id].getTruckState() == TruckState.Stopped){
                                    System.out.println("Truck is working fine but stopped.");
                                }else{
                                    System.out.println("Truck is working fine and is on path " + trucks[id].getPath() + ".");
                                }
                                break;
                            case 3://Repair a truck
                                System.out.print("Enter truck ID: ");
                                id = sc.nextInt();
                                if(trucks[id].getTruckState() == TruckState.Damaged){
                                    //trucks[id].repair();
                                    repairAndRestartTruck(id);
                                }else{
                                    System.out.println("Truck is already working fine.");
                                }
                                break;
                            case 4://Stop a truck
                                System.out.print("Enter truck ID: ");
                                id = sc.nextInt();
                                if(trucks[id].getTruckState() == TruckState.Damaged){
                                    System.out.println("Truck was in critical condition and hence already stopped.");
                                }else if(trucks[id].getTruckState() == TruckState.Stopped){
                                    System.out.println("Truck is already stopped.");
                                }else{
                                    stopTruck(id);
                                    //trucks[id].stop();
                                    System.out.println("Truck stopped!");
                                }
                                break;
                            case 5://Restart a truck
                                System.out.print("Enter truck ID: ");
                                id = sc.nextInt();
                                if(trucks[id].getTruckState() == TruckState.Damaged){
                                    System.out.println("Truck is in critical condition and hence cannot be restarted.");
                                }else if(trucks[id].getTruckState() == TruckState.Stopped){
                                    restartTruck(id);
                                    //trucks[id].restartTruck();
                                    System.out.println("Truck restarted!");
                                }else{
                                    System.out.println("Truck is already enroute!");
                                }
                                break;
                            case 6://Change path of a truck
                                System.out.print("Enter truck ID: ");
                                id = sc.nextInt();
                                if(trucks[id].getTruckState() == TruckState.Damaged){
                                    System.out.println("Truck is in critical condition and hence cannot be assigned to a path.");
                                }else{
                                    System.out.println("Currently on path: " + trucks[id].getPath() + ". Enter new path(between 0 and " + (totalPaths - 1) + "): ");
                                    int newPath = sc.nextInt();
                                    if(newPath < 0 || newPath >= totalPaths){
                                        System.err.println("Invalid Path!");
                                    }else{
                                        try {
                                            trucks[id].changePath(newPath);
                                            System.out.println("Path updated!");
                                            preparedStatement.setInt(4, id);
                                            preparedStatement.setString(3, trucks[id].getTruckState().toString());
                                            preparedStatement.setInt(2, newPath);
                                            String before = "";
                                            try (ResultSet rs = statement.executeQuery("SELECT LOG FROM TRUCKSINFO WHERE TRUCK_ID = " + id)) {
                                                if(rs.next())
                                                    before = rs.getString(1);
                                            }
                                            preparedStatement.setString(1, before + "\n" + new Date(System.currentTimeMillis()) + ": Changed Path!");
                                            preparedStatement.executeUpdate();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                break;
                            case 7://View stopped trucks
                                System.out.println("Trucks in stopped condition:");
                                for(Truck truck : trucks){
                                    if(truck.getTruckState() == TruckState.Stopped){
                                        System.out.print(truck.getID() + ", ");
                                    }
                                }
                                System.out.println();
                                break;
                            case 8://Exit system
                                executor.shutdown();
                    {
                        try {
                            preparedStatement.close();
                            statement.close();
                            connection.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }           
                                System.out.println("Exiting the system...");
                                System.exit(0);
                                break;
                            default:
                                System.err.println("Invalid choice!");
                    }
                    displayOptions();
                    opt = sc.nextInt();
                }
	}

        private void stopTruck(int truckID){
            try {
                trucks[truckID].stop();
                preparedStatement.setInt(4, truckID);
                preparedStatement.setString(3, "Stopped");
                preparedStatement.setInt(2, trucks[truckID].getPath());
                String before = "";
                try (ResultSet rs = statement.executeQuery("SELECT LOG FROM TRUCKSINFO WHERE TRUCK_ID = " + truckID)) {
                    if(rs.next())
                        before = rs.getString(1);
                }
                preparedStatement.setString(1, before + "\n" + new Date(System.currentTimeMillis()) + ": Stopped!");
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void restartTruck(int truckID){
            trucks[truckID].restartTruck();
            executor.execute(trucks[truckID]);
            System.out.println("Truck restarted!");
        }
        
        private void repairAndRestartTruck(int truckID){
            trucks[truckID].repair();
            try {
                //statement.executeUpdate("insert into TRUCK_" + truckID + "_" + new Date(System.currentTimeMillis()).toString().replace("-", "") + " values ('" + new Time(System.currentTimeMillis()) + "', 'Truck repaired!'" + ", " + trucks[truckID].getPath() + ")");
                preparedStatement.setInt(4, truckID);
                preparedStatement.setString(3, "Working");
                preparedStatement.setInt(2, trucks[truckID].getPath());
                String before = "";
                try (ResultSet rs = statement.executeQuery("SELECT LOG FROM TRUCKSINFO WHERE TRUCK_ID = " + truckID)) {
                    if(rs.next())
                        before = rs.getString(1);
                }
                preparedStatement.setString(1, before + "\n" + new Date(System.currentTimeMillis()) + ": Repaired and restarted!");
                preparedStatement.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Truck repaired!");
            restartTruck(truckID);
        }
        
        private void displayOptions(){
            System.out.println("1. View ID of trucks in critical condition.\n2. Show info of a a particular truck.\n3. Repair and restart a truck.\n4. Stop a working truck.\n5. Restart a truck.\n6. Change path of a truck.\n7. View stopped trucks\n8. Exit system.");
        }
        
	public void work(Truck truck){
		// TODO Auto-generated method stub
                truck.time = new Time(new java.util.Date().getTime());
            sensorsData[truck.getID()] = truck.getSensorData();
            ArrayList<Integer> alertingSensors = new ArrayList<>();
            for(int j = 0 ; j < sensorsData[0].length ; j++){
                if(!validSensorData.get(j).contains(sensorsData[truck.getID()][j])){
                //System.out.println("Problem on path " + truck.getPath() + ". Truck " + truck.getID() + " is about to damage.");
                    System.out.println(truck.getPath() + ": " + truck.getID() + " Time of Detection: " + new Time(System.currentTimeMillis()));
                    alertingSensors.add(j);
                }
            }
            
            try {
                if(!alertingSensors.isEmpty()){
                truck.setTruckAsDamaged(alertingSensors);
//                query = "insert into TRUCK_" + truck.getID() + "_" + new Date(System.currentTimeMillis()).toString().replace("-", "") + " values ('" + new Time(System.currentTimeMillis()) + "', 'Check sensors " + alertingSensors + "', " + truck.getPath() + ")";
//                statement.execute(query);
                preparedStatement.setInt(4, truck.getID());
                preparedStatement.setString(3, "ABOUT TO DAMAGE");
                preparedStatement.setInt(2, truck.getPath());
                String before = "";
                try (ResultSet rs = statement.executeQuery("SELECT LOG FROM TRUCKSINFO WHERE TRUCK_ID = " + truck.getID())) {
                    if(rs.next())
                        before = rs.getString(1);
                }
                preparedStatement.setString(1, before + "\n" + new Date(System.currentTimeMillis()) + " " + new Time(System.currentTimeMillis()) + ": Stopped. Check sensors " + alertingSensors);
                preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	
	@Override
	public void notifyOfSensors(Truck truck) {
		// TODO Auto-generated method stub
		work(truck);
	}

}
