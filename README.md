# Shanu

This is an implementation of the 4G LTE eNodeB and 5G New Radio gNB physical layer in Chisel. 
The goal is to provide gateware for use with off the shelf software defined radio front ends like bladeRF.

## Roadmap
- 4G LTE Downlink TX:
  - CRC calculation  - [ x ]
  - Channel Coding:
    - Turbo Coding
  - Rate Matching
  - Code block concatenation
  - Scrambling
  - Modulation
  - Layer Maping/Precoding
  - Precoding
  - Resource Element Mapping
