.SUFFIXES: .c .bc

%.bc : %.c
	clang-12 -g -O0 -c -emit-llvm $< -o $@