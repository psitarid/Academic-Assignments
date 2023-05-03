`timescale 1ns/1ps
module transmission(
  input [10:0]packet,
  input TX_sample_ENABLE,
  output reg TxD = 1'bz,
  output reg Tx_BUSY = 0
);
  
  integer counter = 0;
  //integer counter = 10;
  always@(posedge TX_sample_ENABLE)
    begin
      if (packet != 0) // Packet has arrived
        begin
        if(TX_sample_ENABLE == 1)
          begin
          TxD <= packet[counter];
          end

          if(counter == 10)
          begin
          counter = -1; 
          Tx_BUSY <= 0;
          end
            
        else
          begin 
          counter = counter + 1;
          Tx_BUSY <= 1;
          end
        end
    end
endmodule