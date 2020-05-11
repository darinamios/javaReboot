package ru.pogo.sbrf.cu;

import java.io.OutputStream;
import java.util.*;

public class ATM implements ICollector, IDepositing, IReceiving {
    private HashMap<Integer, Integer> cells;
    private Integer totalSum;

    public ATM(){
        cells = new HashMap<Integer, Integer>();
        totalSum = 0;
    }

    @Override
    public void getTotalSum() {
        System.out.println("Total sum : " + totalSum);
    }

    @Override
    public void getAtmInfo() {
        System.out.println("ATM information...");
        for(var cell : this.cells.entrySet())
            System.out.println(cell.getKey() + " : " + cell.getValue());
        System.out.println("TOTAL" + " : " + totalSum);
    }

    @Override
    public void loadMoney(List<Integer> bills) {
        for(Integer bill : bills){
            setBillToCell(bill);
        }
    }

    @Override
    public List<Integer> receiveMoney(Integer sum) throws Exception {
        var result = new ArrayList<Integer>();
        if (calculateReceive(result, sum) < sum){
            rollbackIssuance(result);
            throw new Exception();
        }
        return result;
    }

    private void setBillToCell(Integer bill){
        if (cells.containsKey(bill)){
            cells.put(bill, cells.get(bill) + 1);
        }
        else {
            cells.put(bill, 1);
        }
        totalSum += bill;
    }

    private Integer calculateReceive(List<Integer> result, Integer sum){
       Integer curSum = 0;
       var sortedCells = getSortCells();
       for(Object key : sortedCells) {
           var bill = (Integer) key;
           if (bill > sum || cells.get(bill) == 0)
               continue;
           var cellValue = cells.get(key);
           for (var i = 0; i < cellValue; i++){
               if ((curSum + bill) > sum) break;
               curSum += addBillToReceive(result, bill);
           }
       }
       return curSum;
   }

    private Integer addBillToReceive(List<Integer> result, Integer bill){
        result.add(bill);
        cells.put(bill, cells.get(bill) - 1);
        totalSum -= bill;
        return bill;
    }

    private Object[] getSortCells(){
        Object[] keys = this.cells.keySet().toArray();
        Arrays.sort(keys, Collections.reverseOrder());
        return keys;
    }

    private void rollbackIssuance(List<Integer> result){
        for (var bill: result){
            setBillToCell(bill);
        }
    }

}
