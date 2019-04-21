package inventory.simulation;


public class Demand {
    public int[]demand=null;
    public int[]days=null;
    public int sum=0;
    public int x =1;
    public int[][] interval; 
    public float []probability;
    public float []com_probability;

    public Demand(int[]demand,int[]days){
        this.demand=demand;
        this.days=days;
        x= this.days.length;
        this.interval=new int [x][2];
        this.probability= new float [x];
        this.com_probability= new float[x];
        this.getsum();
        this.computeProbalility();
        this.computeComulative();
        this.intervalrange();
                
  
    }
    
    private void getsum(){
        try{
        for(int i=0;i<this.days.length;i++){
            this.sum+=this.days[i];

        }

        }catch(Exception e){
        System.out.println(e +" getsum()" );

        }
    }
    private void computeProbalility(){
        try{
             
                   
        for(int i=0;i<this.days.length;i++){
            this.probability[i]=(float)(this.days[i])/(float)(this.sum);

        }
    }catch(Exception e){
        System.out.println(e +" computeProbalility()" );
    }
    }
    
    private void computeComulative(){
        try{
            this.com_probability[0]=this.probability[0];
            
        for(int i=1;i<this.days.length;i++){
            this.com_probability[i]=this.probability[i]+this.com_probability[i-1];
        }
        }catch(Exception e){
            System.out.println(e +" computeComulative()" );
        }
    }
    
    private void intervalrange(){
        try{
            this.interval[0][0]=1;
         this.interval[0][1] =(int) (this.com_probability[0]*100);
        for(int i=1;i<this.x;i++){
               this.interval[i][0] = this.interval[i-1][1]+1;
               this.interval[i][1] =(int) (this.com_probability[i]*100);
        } 
        }catch(Exception e){
            System.out.print(e+ " intervalrange() ");
        }
        
    }
    
    
}
