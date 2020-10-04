import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Week {
    int totalGroup;
    int totalPlayerInAGroup;
    int totalPlayer;

    int[] playerToGroup;
    int[][] groups;

    private Week(){}

    public Week(int totalGroup, int totalPlayerInAGroup) {
        this.totalGroup = totalGroup;
        this.totalPlayerInAGroup = totalPlayerInAGroup;

        totalPlayer=totalGroup*totalPlayerInAGroup;
        playerToGroup=new int[totalPlayer+1];
        groups=new int[totalGroup][totalPlayerInAGroup];

        fillRandomly();
    }

    public void fillRandomly(){
        int[] numbers=new int[totalPlayer];

        for(int i=0;i<totalPlayer;i++)
            numbers[i]=i+1;

        Random random=new Random();
        random.setSeed(new Date().getTime());

        int shuffleAmount=random.nextInt(totalPlayer*5);

        int swapIndex1,swapIndex2,temp;

        for(int i=0;i<shuffleAmount;i++){
            swapIndex1=random.nextInt(totalPlayer);
            swapIndex2=random.nextInt(totalPlayer);

            temp=numbers[swapIndex1];
            numbers[swapIndex1]=numbers[swapIndex2];
            numbers[swapIndex2]=temp;
        }

        for(int i=0;i<totalGroup;i++){
            for(int j=0;j<totalPlayerInAGroup;j++){
                groups[i][j]=numbers[i*totalPlayerInAGroup+j];
                playerToGroup[numbers[i*totalPlayerInAGroup+j]]=i;
            }
        }
    }

    public boolean swapPlayer(int player1,int player2){
        int groupOfPlayer1=playerToGroup[player1];
        int groupOfPlayer2=playerToGroup[player2];

        if(groupOfPlayer1==groupOfPlayer2) return false;

        int[] group1=groups[groupOfPlayer1];
        int[] group2=groups[groupOfPlayer2];

        for(int i=0;i<totalPlayerInAGroup;i++){
            if(group1[i]==player1)
                group1[i]=player2;
            if(group2[i]==player2)
                group2[i]=player1;
        }

        playerToGroup[player2]=groupOfPlayer1;
        playerToGroup[player1]=groupOfPlayer2;

        return true;
    }

    public void print(){
        for(int i=0;i<totalGroup;i++){
            for(int j=0;j<totalPlayerInAGroup;j++){
                System.out.printf("%3d ",groups[i][j]);
            }
            System.out.println();
        }
    }

    public Week copy(){
        Week week=new Week();

        week.totalGroup=totalGroup;
        week.totalPlayerInAGroup=totalPlayerInAGroup;
        week.totalPlayer=totalPlayer;

        week.playerToGroup=new int[playerToGroup.length];

        for(int i=0;i<playerToGroup.length;i++){
            week.playerToGroup[i]=playerToGroup[i];
        }

        week.groups=new int[totalGroup][totalPlayerInAGroup];

        for(int i=0;i<totalGroup;i++){
            for (int j=0;j<totalPlayerInAGroup;j++){
                week.groups[i][j]=groups[i][j];
            }
        }

        return week;
    }

    public int[] getGroupContains(int playerNo){
        return groups[playerToGroup[playerNo]];
    }

    public int getTotalGroup() {
        return totalGroup;
    }

    public void setTotalGroup(int totalGroup) {
        this.totalGroup = totalGroup;
    }

    public int getTotalPlayerInAGroup() {
        return totalPlayerInAGroup;
    }

    public void setTotalPlayerInAGroup(int totalPlayerInAGroup) {
        this.totalPlayerInAGroup = totalPlayerInAGroup;
    }

    public int getTotalPlayer() {
        return totalPlayer;
    }

    public void setTotalPlayer(int totalPlayer) {
        this.totalPlayer = totalPlayer;
    }

    public int[] getPlayer2Group() {
        return playerToGroup;
    }

    public void setPlayer2Group(int[] player2Group) {
        this.playerToGroup = player2Group;
    }

    public int[][] getGroups() {
        return groups;
    }

    public void setGroups(int[][] groups) {
        this.groups = groups;
        updatePayerToGroup();
    }

    private void updatePayerToGroup() {
        for(int i=0;i<totalGroup;i++){
            for (int j=0;j<totalPlayerInAGroup;j++){
                playerToGroup[groups[i][j]]=i;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Week == false) return false;
        return Arrays.deepEquals(((Week)obj).groups,groups);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(groups);
    }
}
