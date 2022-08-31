# Shanu

This is an implementation of the 4G LTE eNodeB and 5G New Radio gNB physical layer in Chisel. 
The goal is to provide gateware for use with off the shelf software defined radio front ends like bladeRF.

## Milestones
#### 4G LTE Downlink TX:
- [ ] PDSCH:
  - [x] CRC (Compute and validate)
  - [ ] Turbo Coding:
    - [ ] Constituent Encoder
    - [ ] Interleaver
    - [ ] Turbo encoder finite state machine 
  - [ ] Rate Matching
  - [ ] Code block concatenation
  - [ ] Scrambling
  - [ ] Modulation
  - [ ] Layer Maping/Precoding
  - [ ] Precoding
  - [ ] Resource Element Mapping

             
