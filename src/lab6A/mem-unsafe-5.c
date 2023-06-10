#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int main (int argc, char **argv) {
   int x = 1;
   char buf [100];
   snprintf (buf, sizeof(buf), argv[1]);
   buf [sizeof(buf)-1] = 0;
   printf("Buffer size is: (%d) \nData input: %s \n", strlen(buf), buf);
   printf("x equals: %d (%#x)\nMemory address for x: (%p) \n",x,x,&x);
   return 0;
}
