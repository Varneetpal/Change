import java.sql.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class Change {
    ArrayList<ArrayList<Double>> geeks = new ArrayList<>();
    public Change(){
        super();
    }

    public void findChange(double totalChange, double currentTotal, ArrayList<Double> coinsUsed, ArrayList<Double> possibleCoins){
        if (totalChange == 0){
            return;
        }
        if (totalChange < 0){
            return;
        }
        if (currentTotal == totalChange){
            ArrayList<Double> coins = new ArrayList<>(coinsUsed);
            geeks.add(coins);

        }
        if (currentTotal > totalChange){
            return;
        }
        else{
            for (int i = 0; i<possibleCoins.size(); i++){
                double temp = possibleCoins.get(i);

                if (coinsUsed.isEmpty() || temp >= coinsUsed.get(coinsUsed.size()-1)){

                    coinsUsed.add(temp);
                    findChange(totalChange, currentTotal+temp, new ArrayList<>(coinsUsed), new ArrayList<>(possibleCoins));
                    coinsUsed.remove(coinsUsed.size()-1);
                }
            }
        }

        /*
        CheckAddition(currentTotal, c, numbers[], numbersUsed[]){
            if (totalChange == currentChange){
                Add usedCoins to solution set, that is to geeks.
            }
            else{
                for i starting from 0 to len(possibleCoins[]){
                    temp = possibleCoins[i];
                    if (CoinsUsed is empty || temp is greater than or equal to last element in coinsUsed){
                        Add temp to numbersUsed;
                        checkAddition(currentTotal+temp, c, numbers, numbersUsed);
                        Remove the last element from numbersUsed;
                    }
                }
            }
           }
         */
    }

    public ArrayList<Double> findShortest(ArrayList<ArrayList<Double>> geeks){
        ArrayList<Double> shortest = geeks.stream().min(Comparator.comparing(ArrayList::size)).orElse(new ArrayList<>());
        return shortest;
    }

    public String shortestInString(ArrayList<Double> shortestSolution){
        HashMap<Double, Integer> coinCounter = new HashMap<>();
        for (int i = 0; i < shortestSolution.size(); i++){
            Double temp = shortestSolution.get(i);
            if (coinCounter.containsKey(temp)){
                int counterTemp = coinCounter.get(temp);

                coinCounter.put(temp, counterTemp+1);
            }
            else{
                coinCounter.put(temp, 1);
            }
        }
        String result;
        result = coinCounter.entrySet().stream().map(entry -> entry.getValue() + "@" + entry.getKey()).reduce((a, b) -> a + ", " + b).orElse("");

        return result;
    }
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("Provide proper params");
            return;
        }
        Double changeAmount = Double.parseDouble(args[0]);

        Change change = new Change();
        ArrayList<Double> possibleCoins = new ArrayList<>();
        for (int i = 1; i < args.length; i++){
            possibleCoins.add(Double.parseDouble(args[i]));
        }
        change.findChange(changeAmount, 0, new ArrayList<>(), new ArrayList<>(possibleCoins));
        if (change.geeks.isEmpty()){
            System.out.println("impossible");
        }
        if (change.geeks.size()!=0){
            System.out.println(change.geeks.size() + ": " + change.shortestInString(change.findShortest(change.geeks)));
        }
    }
}