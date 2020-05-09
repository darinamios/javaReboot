package ru.pogo.sbrf.cu;

public class Benchmark implements BenchmarkMBean {
    private final int loopCounter;
    private volatile int size = 0;
    private final Integer MAX_LENGTH =  100;

    public Benchmark( int loopCounter) {
        this.loopCounter = loopCounter;
    }

     void run() throws InterruptedException {
        for (var i = 0; i < loopCounter; i ++){
            var tmpArray = new String[size];
            var resultString = "";
            for(var j = 0; j < size; j ++){
                Double sq = (Math.sqrt(j));
                tmpArray[j] = getFormattedString(sq, j).trim().toLowerCase();
                if (resultString.length() + tmpArray[j].length() < MAX_LENGTH) {
                    resultString += tmpArray[j];
                }
                else {
                    //System.out.println(resultString);
                    resultString = tmpArray[j];
                }
            }
            //System.out.println(resultString);
            Thread.sleep(10);
        }
        /*for ( int idx = 0; idx < loopCounter; idx++ ) {
            int local = size;
            Object[] array = new Object[ local ];
            for ( int i = 0; i < local; i++ ) {
                array[ i ] = new String( new char[ 0 ] );
            }
            Thread.sleep( 10 ); //Label_1
        }*/
    }
    public int getSize() {
        return size;
    }

    public void setSize( int size ) {
        System.out.println( "new size:" + size );
        this.size = size;
    }

    private String getFormattedString(Double d, Integer i){
        String result = "";
        if (d > 0 && i > 0){
            result = String.format(" Math.sqrt(%d) = %f ", i, d);
        }
        return result;
    }

}
