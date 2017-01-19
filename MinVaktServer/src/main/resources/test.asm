// What is this, I don't even

Vector_Table:
    .word  0x20000400                   // Top of stack (same as kernel stack)
    .word  _start+1                     // Start address
    .word  default_handler+1            // Odd addresses = Thumb addresses
    .word  default_handler+1
    .word  0
    .word  0
    .word  0
    .word  0
    .word  0
    .word  0
    .word  0
    .word  default_handler+1
    .word  0
    .word  0
    .word  default_handler+1
    .word  scheduler+1