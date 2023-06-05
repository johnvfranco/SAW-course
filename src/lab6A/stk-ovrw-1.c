#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

char *name[2];

void g() {  execve(name[0], name, 0x0); }

long *f (long *x) {
   *(&x+11) = (long*)g;
   return 0;
}

void h(int thevariable) {
   long *s = (long*)123;
   if (thevariable > 10) f(s);
}

int main (int argc, char **argv) {
   if (argc < 2) {
      printf("Usage: stk-ovrw-1 <positive-integer>\n");
      return 0;
   }
	name[0] = (char*)malloc(20);
	strcpy(name[0],"/bin/dash");
	name[1] = 0x0;
   h(atoi(argv[1]));
   return 0;
}

/* Result:
[franco@franco ReverseEngineering]$ stack-overwrite-64 45

h (stack addr): &s=7fffffffde30, main: 55555555495d, g:5555555547ca

$

Note: int *s = (int *)123; means the address of s is the address of a location
  on h's stack
Note: in int *f (int *x) the address of x is the address of a location on the 
  stack
Note: &s and &x are almost the same so we have access to h's stack frame 
  from function f!
Note: the address to return to from f is in h's stack frame so it can be
  overwritten!!
*/
