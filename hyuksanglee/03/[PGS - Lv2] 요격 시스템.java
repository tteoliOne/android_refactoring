import java.util.*;

class Solution {
    public int solution(int[][] targets) {
        int answer = 0;
        
        Arrays.sort(targets, (o1, o2) -> {
            if (o1[1] == o2[1]) {
                return Integer.compare(o1[0], o2[0]);
            }
            return Integer.compare(o1[1], o2[1]);
        });
        int start = -1;
        int end = -1;
        for(int i = 0; i<targets.length; i++){
            int[] target = targets[i];
            if(end<=target[0]){
                answer++;
                start=target[0];
                end = target[1];
            }
        }
        
        return answer;
    }
}
