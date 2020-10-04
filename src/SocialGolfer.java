import java.util.HashSet;
import java.util.Random;
import java.util.Vector;

public class SocialGolfer {
    int totalWeek;
    int totalPlayer;

    Week[] weeks;

    int conflictCount;

    private SocialGolfer(){}

    public SocialGolfer(int totalGroup, int totalPlayerInAGroup, int totalWeek) {
        this.totalWeek = totalWeek;
        totalPlayer=totalPlayerInAGroup*totalGroup;

        weeks=new Week[totalWeek];
        for(int i = 0;i < weeks.length;i++){
            weeks[i]=new Week(totalGroup,totalPlayerInAGroup);
        }
        //initializeWeeks(totalPlayerInAGroup,totalGroup);
        randomShuffle();
        setConflictCount();
    }

    private void setConflictCount(){
        boolean[] playerInGroup;
        int conflictCount=0;

        for(int i=1;i<=totalPlayer;i++){
            playerInGroup=new boolean[totalPlayer+1];
            for (Week week:weeks) {
                int[] group=week.getGroupContains(i);

                for(int player:group){
                    if(playerInGroup[player])
                        conflictCount++;
                    else
                        playerInGroup[player]=true;
                }

                playerInGroup[i]=false;
            }
        }

        this.conflictCount=conflictCount/2;
    }

    public HashSet<SocialGolfer> getNeighbours(){
        HashSet<SocialGolfer> neighbours=new HashSet<>();

        SocialGolfer temp;

        for(int i=1;i<=totalPlayer;i++){
            for(int j=i+1;j<=totalPlayer;j++){
                for(int k=1;k<totalWeek;k++){
                    temp=copy();
                    if(temp.weeks[k].swapPlayer(i,j)){
                        temp.setConflictCount();

                        if(!temp.equals(this))
                            neighbours.add(temp);
                    }
                }
            }
        }

        return neighbours;
    }

    public void randomShuffle(){
        Random random=new Random();

        for (int i=0;i<3;i++){
            int randIndex = random.nextInt(weeks.length);

            weeks[randIndex].swapPlayer(
                    random.nextInt(totalPlayer) + 1,
                    random.nextInt(totalPlayer) + 1);

            weeks[randIndex].swapPlayer(
                    random.nextInt(totalPlayer) + 1,
                    random.nextInt(totalPlayer) + 1);
        }
    }

    public SocialGolfer copy(){
        SocialGolfer socialGolfer=new SocialGolfer();

        socialGolfer.totalWeek=totalWeek;
        socialGolfer.totalPlayer=totalPlayer;

        socialGolfer.weeks=new Week[totalWeek];
        for(int i=0;i<totalWeek;i++){
            socialGolfer.weeks[i]=weeks[i].copy();
        }
        return socialGolfer;
    }

    public void print(){
        System.out.println("\n");
        for(int i=0;i<totalWeek;i++){
            System.out.println("Week: "+(i+1));
            weeks[i].print();
            System.out.println("---------------\n");
        }

        System.out.println("--------------------------------------------------------------");
        System.out.println("--------------------------------------------------------------");
    }

    public int getTotalWeek() {
        return totalWeek;
    }

    public void setTotalWeek(int totalWeek) {
        this.totalWeek = totalWeek;
    }

    public Week[] getWeeks() {
        return weeks;
    }

    public void setWeeks(Week[] weeks) {
        this.weeks = weeks;
    }

    public int getConflictCount() {
        return conflictCount;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof SocialGolfer)) return false;

        SocialGolfer s=(SocialGolfer) obj;

        for(int i = 0;i < weeks.length;i++){
            if(!weeks[i].equals(s.weeks[i]))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash=0;

        for (Week week : weeks) {
            hash += week.hashCode();
        }

        return hash;
    }
}
