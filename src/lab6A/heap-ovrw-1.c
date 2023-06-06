/* A simple demonstration of a heap based overflow */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main() {
   long diff, size = 8;
   char *buf1;
   char *buf2;

   buf1 = (char*)malloc(size);
   buf2 = (char*)malloc(size);

   if (buf1 == NULL || buf2 == NULL) {
      perror("malloc");
      exit(-1);
   }
   
   diff =  (unsigned long)buf2 - (unsigned long)buf1;
   printf("buf1 = %p, buf2 = %p, diff = %ld\n", buf1, buf2, diff);
   memset(buf2, '2', size);
   printf("BEFORE: buf2 = %s\n", buf2);
   memset(buf1, '1', diff+3);  /* We overwrite 3 chars */
   printf("AFTER:  buf2 = %s\n", buf2);

   return 0;
}
/*
  prompt> make heap-ovf-exp-1
  prompt> heap-ovf-exp-1
  buf1 = 0x1500010 & buf2 = 0x1500030 & diff = 32
  BEFORE: buf2 = 22222222
  AFTER:  buf2 = 11122222
*/
