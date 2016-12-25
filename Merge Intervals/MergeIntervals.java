import java.util.Random;
import java.util.Arrays;
import java.util.*;

public class MergeIntervals{
    private static class Interval {
       int start;
       int end;
       Interval() { start = 0; end = 0; }
       Interval(int s, int e) { start = s; end = e; }
   }

   public List<Interval> mergeIntervals(Interval[] intervals) {
     intervals = mergeSortIntervals(0, intervals.length - 1, intervals);

     List<Interval> ints = new ArrayList<>();
     Interval intToAdd = intervals[0];
     for(int cp = 1 ; cp < intervals.length ; cp++){
         while(cp < intervals.length && intervals[cp].start == intToAdd.start){
             // Overlap. Events at same time!
             intToAdd.end = intervals[cp].end;
             cp++;
         }
         if(cp < intervals.length && intervals[cp].start <= intToAdd.end){
             // overlap with previous event
             if(intervals[cp].end <= intToAdd.end){
                 // This event is shorter. Remove other
                //  lp = cp;
             }else{
                 // This is the longer event.
                intToAdd.end = intervals[cp].end;
             }

         }else{
             // Create new interval.
             ints.add(intToAdd);
             intToAdd = null;
             if(cp < intervals.length)
             intToAdd = intervals[cp];
         }
     }
     if(intToAdd != null) ints.add(intToAdd);
     return ints;
   }

   public void printIntervals(Interval[] intervals){
     for(Interval interval : intervals){
       System.out.print("[" + interval.start + ", " + interval.end + "], ");
     }
   }

   public void printIntervals(List<Interval> intervals){
     for(Interval interval : intervals){
       System.out.print("[" + interval.start + ", " + interval.end + "], ");
     }
   }

   private Interval[] mergeSortIntervals(int l, int r, Interval[] intervals){
     if(l >= r) return intervals;
     int mid = (intervals.length - 1) / 2;
     Interval[] left = mergeSortIntervals(0, mid, Arrays.copyOfRange(intervals, 0, mid + 1));
     Interval[] right = mergeSortIntervals(mid + 1, intervals.length - 1, Arrays.copyOfRange(intervals, mid + 1, intervals.length));

     int m = 0;
     int i = 0;l = 0;
     for( ; i < intervals.length && l < left.length && m < right.length; i++){
       if(left[l].start > right[m].start){
         intervals[i] = right[m];
         m++;
       }else if(left[l].start < right[m].start){
         intervals[i] = left[l];
         l++;
       }else{
         if(left[l].end < right[m].end){
           intervals[i] = left[l];
           l++;
         }else{
           intervals[i] = right[m];
           m++;
         }
       }
     }

     if(l < left.length){
       while(i < intervals.length){
         intervals[i] = left[l];
         l++;i++;
       }
     }else if(m < right.length){
       while(i < intervals.length){
         intervals[i] = right[m];
         m++;i++;
       }
     }
     return intervals;
   }

  public static void main(String[] args) {
    Random random = new Random();

    int totalIntervals = random.nextInt(40);

    Interval [] intervals = new Interval[totalIntervals];

    for(int i = 0 ; i < totalIntervals ; i++){
      int st = random.nextInt(20);
      int et = st + random.nextInt(5) + 1;

      intervals[i] = new Interval(st, et);
    }

    MergeIntervals obj = new MergeIntervals();

    System.out.println("Intervals: ");
    obj.printIntervals(intervals);
    System.out.print("\nTotal Intervals: " + totalIntervals + "\nIntervals after Merging: ");
    List<Interval> mergedIntervals = obj.mergeIntervals(intervals);
    obj.printIntervals(mergedIntervals);
  }
}
