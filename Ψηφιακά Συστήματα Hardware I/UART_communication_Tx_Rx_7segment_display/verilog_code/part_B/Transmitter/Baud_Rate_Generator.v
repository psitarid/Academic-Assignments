`timescale 1ns/1ps

module baud_rate_generator(
  input Clk, //50MHz
  input reset,
  input [2:0] baud_select,
  output reg TX_sample_ENABLE = 0
);

integer counter = 0;

always @ (posedge Clk or negedge reset)
    begin 
      TX_sample_ENABLE <= 0;
      
      if (!reset)
        counter = 0 ;
      
      else 
        begin
        case (baud_select)     
          3'b000:
            if (counter == 166666)
              begin
                  TX_sample_ENABLE <= 1;
                  counter = 0;
              end
            else begin 
              counter = counter + 1;
            end

          3'b001:
            if (counter == 41666)
              begin
                TX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin 
              counter = counter + 1;
            end
          3'b010:
            if (counter == 10416)
              begin
                TX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end

          3'b011:
            if (counter == 5208)
              begin
                TX_sample_ENABLE <= 1;
                counter <= 0;
              end
            else begin
              counter = counter + 1;
            end

          3'b100:
            if (counter == 2604)
              begin
                TX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end

            3'b101:
              if (counter == 1302)
                 begin 
                TX_sample_ENABLE <= 1;
                  counter = 0;
                 end
              else begin
                counter = counter + 1;
              end

          3'b110:
            if (counter == 868)
              begin
                TX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end
          
          3'b111:
            if (counter == 434)
              begin
                TX_sample_ENABLE <= 1;
                counter = 0;
              end
            else begin
              counter = counter + 1;
            end
        endcase
        end
    end
  
  
  endmodule