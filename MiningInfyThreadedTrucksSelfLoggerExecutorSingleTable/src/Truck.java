import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


enum TruckState{Working, Damaged, Stopped}
public class Truck implements Runnable{
	
	private TruckListener listener;
	
	private int sensors[];
        
	private int id;
	
        public java.sql.Time time;
        
        private ArrayList<Integer> aboutToDamagedParts;
        
	private int path;
	
	private TruckState truckState;
	
	public Truck(int ID, int path) {
		// TODO Auto-generated constructor stub
		sensors = new int[1000]; //default number of sensors
		id = ID;
		this.path = path;
		truckState = TruckState.Working;
	}
	
	public Truck(int totalSensors, int ID, int path) {
		// TODO Auto-generated constructor stub
		sensors = new int[totalSensors]; //specific number of sensors
		id = ID;
		this.path = path;
		truckState = TruckState.Working;
	}
	
	public int getID(){
		return id;
	}
	
	public int getPath(){
		return path;
	}
	
	public int getSensorCount(){
		return sensors.length;
	}
	
	public void generateSensorData(){
		Random generator = new Random();
		for(int i = 0 ; i < sensors.length ; i++){
			if(generator.nextInt(10000) > 9995)
				sensors[i] = generator.nextInt(10001);
			else
				sensors[i] = 1000;
		}
	}
	
	public int[] getSensorData(){
		return sensors;
	}
	
	public void addListener(Receiver receiver){
		listener = receiver;
	}
	
	private void notifyListener(){
		listener.notifyOfSensors(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
                    if(truckState == TruckState.Working){
			generateSensorData();
                    }
		}finally{
			notifyListener();
			try {
				if(truckState == TruckState.Working){
					Thread.sleep(1);
					run();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void setTruckAsDamaged(ArrayList<Integer> aboutToDamagePartID){
		truckState = TruckState.Damaged;
                aboutToDamagedParts = aboutToDamagePartID;
	}
        
        public void stop(){
            truckState = TruckState.Stopped;
	}
        
        public TruckState getTruckState(){
            return truckState;
        }
        
        public ArrayList<Integer> getDamagedPartsIDs(){
            return aboutToDamagedParts;
        }
        
        public void repair(){
            aboutToDamagedParts = null;
            restartTruck();
        }
        
        public void restartTruck(){
            truckState = TruckState.Working;
        }
        
        public void changePath(int newPath){
            path = newPath;
        }
}
