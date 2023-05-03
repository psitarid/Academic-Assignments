`timescale 1ns/1ps
// counter based
module clock_divider(
  input Clk , //50MHz
  input reset,
  output reg [3:0] anode,
  output reg [3:0] anode2
);
  

  reg [3:0] counter = 4'b1100;

  always @ (posedge Clk or negedge reset)
    begin 
      if (!reset)
        counter <= 4'b1100 ;
      else
        if (counter == 16)
          counter <= 0;
      else counter <= counter + 1;
    end
  
 
  always @ (counter)
    	begin
          case(counter)
          4'b0001 : anode = 4'b0111;  
          4'b0101 : anode = 4'b1011;
          4'b1001 : anode = 4'b1101;     
          4'b1101 : anode = 4'b1110; 
          
          default: anode = 4'b1111;
          endcase
          
        
          case(counter)
          4'b1111 : anode2 = 4'b0111;
          4'b0011 : anode2 = 4'b1011;
          4'b0111 : anode2 = 4'b1101;
          4'b1011 : anode2 = 4'b1110;
            
            
          default: anode2 = 4'b1111;
                 
          endcase 
          
   
        end
          
endmodule