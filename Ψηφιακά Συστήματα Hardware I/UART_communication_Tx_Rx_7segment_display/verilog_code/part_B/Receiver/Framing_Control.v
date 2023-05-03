`timescale 1ns/1ps

module framing_control(
	input start_bit_sync,
  	output reg Rx_FERROR = 0
);
  
  always @(start_bit_sync)
    begin
      if(start_bit_sync == 1)
        begin
          Rx_FERROR <= 0; // Synchronization is successful.
        end
    end
  
endmodule
