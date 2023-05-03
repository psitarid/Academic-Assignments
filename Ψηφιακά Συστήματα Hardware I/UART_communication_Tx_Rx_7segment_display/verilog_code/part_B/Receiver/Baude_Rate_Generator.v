`timescale 1ns/1ps

module baud_rate_generator(
  input Clk, //50MHz
  input reset,
  input [2:0] baud_select,
  output reg RX_sample_ENABLE = 0
);

integer counter = 0;
integer baud_rate = 0;
integer max_counter = 0;
  
  
  always @ (baud_select)
    begin 
      case (baud_select)
        3'b000 : baud_rate = 300; 
        3'b001 : baud_rate = 1200;
        3'b010 : baud_rate = 4800;
        3'b011 : baud_rate = 9600;
        3'b100 : baud_rate = 19200; 
        3'b101 : baud_rate = 38400;
        3'b110 : baud_rate = 57600;
        3'b111 : baud_rate = 115200;
        default : baud_rate = 0;
      endcase
    end

  
  
  
  always @ (baud_rate)
    begin
      case (baud_rate)
        //(Tsc = 1/16*baud_rate)
        300    : max_counter = 10416; // 166666/16 rounded down
    
        1200   : max_counter = 2604; //41666/16 rounded down
        
        4800   : max_counter = 651; //10416/16 rounded down
  
        9600   : max_counter = 325; //5208/16 rounded down
      
        19200  : max_counter = 162; //2604/16 rounded down
       
        38400  : max_counter = 81; //1302/16 rounded down
      
        57600  : max_counter = 54; // 828/16 rounded down
        
        115200 : max_counter = 27; // 434/16 rounded down
      
        default : max_counter = 0;
      endcase
    end
  
  
  
  
  always @(posedge Clk or negedge reset)
      begin 
        if (reset == 0)
          begin
            counter <= 0;
            RX_sample_ENABLE <= 0;
          end
        else if (counter == max_counter - 1)
          begin
            RX_sample_ENABLE <= 1;
            counter <= 0;
          end
        else if (counter == 0)
          begin
            RX_sample_ENABLE <= 0;
            counter <= counter + 1;
          end
        else
          counter <= counter + 1;
      end
  
  
  
  
  
 /* 
always @ (posedge Clk or negedge reset)
    begin 
     //RX_sample_ENABLE <= 0;
      
      
      if (!reset)
        begin
        RX_sample_ENABLE <= 0;
        counter = 0 ;
        end
      
      else    
        begin                          
        case (baud_select)     
          3'b000:
            if (counter == 10416) // 166666/16 rounded down
              begin
                  RX_sample_ENABLE <= 1;
                  counter = 0;
              end
            else begin 
              counter = counter + 1;
            end

          3'b001:
            if (counter == 2604) //41666/16 rounded down
              begin
                RX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin 
              counter = counter + 1;
            end
          3'b010:
            if (counter == 651) //10416/16 rounded down
              begin
                RX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end

          3'b011:
            if (counter == 325) //5208/16 rounded down
              begin
                RX_sample_ENABLE <= 1;
                counter <= 0;
              end
            else begin
              counter = counter + 1;
            end

          3'b100:
            if (counter == 162) //2604/16 rounded down
              begin
                RX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end

            3'b101:
              if (counter == 81) //1302/16 rounded down
                 begin 
                RX_sample_ENABLE <= 1;
                  counter = 0;
                 end
              else begin
                counter = counter + 1;
              end

          3'b110:
            if (counter == 54) // 828/16 rounded down
              begin
                RX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end
          
          3'b111:
           
            if(counter == 26) // 434/16
              begin
                RX_sample_ENABLE <=1;
                counter <= 0;
              end

            else if (counter == 0)
              begin
              RX_sample_ENABLE <=0;
                counter <= counter + 1;
              end

            else
              begin
              counter <= counter + 1;
              end
                     
        endcase
           
        end
              
    end
  */
  
  
  endmodule