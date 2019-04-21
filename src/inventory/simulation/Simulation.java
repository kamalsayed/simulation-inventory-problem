
package inventory.simulation;

import java.util.ArrayList;


public class Simulation {
    public int [] demm;
    public int [] ledd_rn;
    public int [] ledd;
    public int beginning_Inv;
    public int []beg_inv;
    public int [] Ending_Inv;
    public int Order_Quantity;
    public int Reorder_point;
    public int No_of_Days;
    public int [] RN_Demand; //random number for demand
    public int [] RN_LD; //random number for leadtime
    public Demand demand ;
    public Demand LeadTime;
    public int []lost_sales;
    public boolean [] Order;
    public int [] Demand;
    public int [] Demand_freq;
    public int [] Lead;
    public int [] Lead_freq;
    public int [] units_received;
    public float avg_ending=0;
    public float avg_lost=0;
    public float avg_placed=0;
    public Simulation(int beginning_inv,int Order_Quantity,int Reorder_point,int no_ofDays,int [] RN_D,int [] RN_LD,int [] Demand,int [] Demand_freq,int [] Lead,int [] Lead_freq){
        try{
      this.ledd_rn= new int[no_ofDays];
      this.demm= new int[no_ofDays];
      this.ledd = new int [no_ofDays];
      this.beg_inv = new int[no_ofDays];
      this.beg_inv[0]=beginning_inv;
      this.units_received = new int[no_ofDays];      
      this.No_of_Days=no_ofDays;
      this.Order=new boolean [no_ofDays];
      this.Order[0]=false;
      this.lost_sales = new int[no_ofDays];
      this.beginning_Inv=beginning_inv;
      this.Ending_Inv= new  int[no_ofDays];
      this.Order_Quantity=Order_Quantity;
      this.Reorder_point=Reorder_point;
      this.RN_Demand=RN_D;
      this.RN_LD=RN_LD;
      this.Demand=Demand;
      this.Demand_freq=Demand_freq;
      this.Lead=Lead;
      this.Lead_freq=Lead_freq;   
      demand = new Demand(this.Demand,this.Demand_freq);
      LeadTime = new Demand(this.Lead,this.Lead_freq);
      this.avg_ending=0;
      this.avg_lost=0;
      this.avg_placed=0;
      }catch(Exception e){
          System.out.println(e);
      }
    }
    
  
    public void Sim(){
        try{
        int dem_class=0;
        int ld_class=-1;
        int ld_counter=0;
        
        for(int x=1;x <= this.No_of_Days;x++){
            this.beg_inv[x-1]=this.beginning_Inv;
            if(ld_class>0){// if there's a placed order minus the lead time by one
               ld_class--;
            }
            else if(ld_class==0){//check for arrived orders
                
                this.beginning_Inv+=this.Order_Quantity;
                this.beg_inv[x-1]=this.beginning_Inv;
                this.units_received[x-1]=this.Order_Quantity;
                ld_class=-1;
            }
            if(ld_counter==this.RN_LD.length){
                ld_counter=0;
            }
            
            
            this.Order[x-1]=false; //default until we prove some thing else
            
                for(int i=0;i<this.Demand_freq.length;i++){ //to get the demand for a specific RN
                    if(this.RN_Demand[x-1]==this.demand.interval[i][1]||this.RN_Demand[x-1]==this.demand.interval[i][0]){
                         dem_class=demand.demand[i];
                         this.demm[x-1]=dem_class;
                        break;
                    }
                    else if((this.RN_Demand[x-1]<this.demand.interval[i][1]&&this.RN_Demand[x-1]>this.demand.interval[i][0])){
                        dem_class=demand.demand[i];
                        this.demm[x-1]=dem_class;
                        break;
                    }
                    
                }
                 if(dem_class>=this.beginning_Inv){
                    if(dem_class>this.beginning_Inv){
                    this.lost_sales[x-1]= dem_class - this.beginning_Inv;
                    }
                    this.Ending_Inv[x-1]=0;
                    //this.beginning_Inv=0;
                    
                    if(ld_class>=0){
                    this.Order[x-1]=false;   
                    }
                    else{
                        this.Order[x-1]=true;
                    }
                }
                 else if(dem_class<this.beginning_Inv){
                    this.Ending_Inv[x-1]=this.beginning_Inv-dem_class;
                    //this.beginning_Inv=this.Ending_Inv[x-1];
                    if(this.Ending_Inv[x-1]<=this.Reorder_point){
                    if(ld_class>=0){
                    this.Order[x-1]=false;   
                    }
                    else{
                        this.Order[x-1]=true;
                    }   
                    }    
                }
                if(this.Order[x-1]==true){//error
                    for(int i=0;i<this.RN_LD.length;i++){ //to get the lead for a specific RN
                    if((this.RN_LD[ld_counter]>this.LeadTime.interval[i][0]&&this.RN_LD[ld_counter]<this.LeadTime.interval[i][1])){
                        ld_class=LeadTime.demand[i];
                        this.ledd[ld_counter]=ld_class;
                        this.ledd_rn[ld_counter]=this.RN_LD[ld_counter];
                        ld_counter++;
                        break;
                    }
                    else if(this.RN_LD[ld_counter]==this.LeadTime.interval[i][0] || this.RN_LD[ld_counter]==this.LeadTime.interval[i][0]){
                        ld_class=LeadTime.demand[i];
                        this.ledd[ld_counter]=ld_class;
                        this.ledd_rn[ld_counter]=this.RN_LD[ld_counter];
                        ld_counter++;
                        break;
                    }
                    
                    }  
                }
                
              this.beginning_Inv=this.Ending_Inv[x-1];  
        }
        

        
        this.avg_ending=(float)(sumarray(this.Ending_Inv))/this.No_of_Days;
        this.avg_lost=(float)(sumarray(this.lost_sales))/this.No_of_Days;
        this.avg_placed=(float)(count(this.Order))/this.No_of_Days;
         }catch(Exception e){
          System.out.println(e);
      }
  
        
    }
    
    
        public int sumarray(int [] arr){
            try{
        int sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];
        }
        return sum;
         }catch(Exception e){
          System.out.println(e);
        }
        return 0;
        }
        
        public int count(boolean [] arr){
            try{
            int sum=0;
            for(int i=0;i<arr.length;i++){
                if(arr[i]==true)
                   sum+=1;
            } 
            return sum;
             }catch(Exception e){
          System.out.println(e);
        }
         return 0;
        }
    
    public float stockoutcost(float cost){
        try{
        float daily_stockout=(float)(cost*this.avg_lost);
        
        return daily_stockout;
         }catch(Exception e){
          System.out.println(e);
      }
        return 0;
    }
    public float daily_order(float cost){
        try{
        float daily_order =(float)(cost * this.avg_placed);
        return daily_order;
         }catch(Exception e){
          System.out.println(e);
      }
        return 0;
    }
    public float daily_holding(float cost){
        try{
        float daily_holding= (float)(cost * this.avg_ending);
        return  daily_holding;
         }catch(Exception e){
          System.out.println(e);
        }
        return 0;
    }
    
    
}
