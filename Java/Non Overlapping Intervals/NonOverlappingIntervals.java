import java.util.Random;
import java.util.Arrays;

public class NonOverlappingIntervals{
    private static class Interval {
       int start;
       int end;
       Interval() { start = 0; end = 0; }
       Interval(int s, int e) { start = s; end = e; }
   }

   public int eraseOverlapIntervals(Interval[] intervals) {
     intervals = mergeSortIntervals(0, intervals.length - 1, intervals);

     int removals = 0;
     int lp = 0;
     for(int cp = 1 ; cp < intervals.length ; cp++){
         while(cp < intervals.length && intervals[cp].start == intervals[lp].start){
             cp++;
             removals++;
         }
         if(cp < intervals.length && intervals[cp].start < intervals[lp].end){
             // overlap with previous event
             if(intervals[cp].end < intervals[lp].end){
                 // This event is shorter. Remove other
                 lp = cp;
             }
                 removals++;
         }else{
             lp = cp;
         }
     }

     return removals;
   }

   public void printIntervals(Interval[] intervals){
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
      int st = random.nextInt(10);
      int et = st + random.nextInt(10);

      intervals[i] = new Interval(st, et);
    }

    NonOverlappingIntervals obj = new NonOverlappingIntervals();

    System.out.println("Intervals: ");
    obj.printIntervals(intervals);
    System.out.println("\nTotal Intervals: " + totalIntervals + "\nIntervals needed to be removed: " + obj.eraseOverlapIntervals(intervals));
  }
}

/*

///////////// LEET CODE CHALLENGE ////////////////

Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the intervals non-overlapping.

Note:
You may assume the interval's end point is always bigger than its start point.
Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
Example 1:
Input: [ [1,2], [2,3], [3,4], [1,3] ]

Output: 1

Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
Example 2:
Input: [ [1,2], [1,2], [1,2] ]

Output: 2

Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
Example 3:
Input: [ [1,2], [2,3] ]

Output: 0

Explanation: You don't need to remove any of the intervals since they're already non-overlapping.

*/
