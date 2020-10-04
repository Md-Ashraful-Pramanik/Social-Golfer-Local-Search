import java.util.HashSet;
import java.util.Stack;

public class LocalSearch {

    public static void main(String[] args) {

        int totalGroup=5;
        int totalPlayerInAGroup=3;
        int totalWeek=7;
        while (true){
            HashSet<SocialGolfer> visited = new HashSet<>(10000);
            HashSet<SocialGolfer> neighbours;

            SocialGolfer current;

            Stack<SocialGolfer> next = new Stack<>();
            next.push(new SocialGolfer(totalGroup,totalPlayerInAGroup,totalWeek));

            int step=0;
            int minConflictCount = Integer.MAX_VALUE;
            int prevConflictCount = Integer.MAX_VALUE;

            while (!next.empty()){
                step++;

                current=next.pop();

                if(current.getConflictCount() == 0) {
                    current.print();
                    System.out.println("Step: " + step);
                    return;
                }

                if(visited.contains(current))
                    continue;

                visited.add(current);
                minConflictCount = current.getConflictCount();

                neighbours = current.getNeighbours();

                for (SocialGolfer neighbour : neighbours){
                    if(neighbour.getConflictCount() <= minConflictCount){
                        if(minConflictCount > neighbour.getConflictCount())
                            next.clear();

                        next.push(neighbour);
                        minConflictCount = neighbour.getConflictCount();
                    }
                }

                if(prevConflictCount > minConflictCount)
                    visited.clear();

                prevConflictCount = minConflictCount;

//            if(step%10 == 0){
//                System.out.println("Step: " + step + ", Conflict: " + current.getConflictCount());
//                System.out.println(neighbours.size());
//                System.out.println(next.size());
//                System.out.println(visited.size());
//            }
            }

            if(next.empty())
                System.out.println("Stuck in Local optima. Steps: " + step + ". Min Conflict: " + minConflictCount);
        }
    }
}
