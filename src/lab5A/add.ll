; ModuleID = 'add.bc'
source_filename = "add.c"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

@.str = private unnamed_addr constant [4 x i8] c"%u\0A\00", align 1

; Function Attrs: noinline nounwind optnone uwtable
define dso_local i32 @add(i32 %0, i32 %1) #0 !dbg !7 {
  %3 = alloca i32, align 4
  %4 = alloca i32, align 4
  store i32 %0, i32* %3, align 4
  call void @llvm.dbg.declare(metadata i32* %3, metadata !15, metadata !DIExpression()), !dbg !16
  store i32 %1, i32* %4, align 4
  call void @llvm.dbg.declare(metadata i32* %4, metadata !17, metadata !DIExpression()), !dbg !18
  %5 = load i32, i32* %3, align 4, !dbg !19
  %6 = load i32, i32* %4, align 4, !dbg !20
  %7 = add i32 %5, %6, !dbg !21
  ret i32 %7, !dbg !22
}

; Function Attrs: nofree nosync nounwind readnone speculatable willreturn
declare void @llvm.dbg.declare(metadata, metadata, metadata) #1

; Function Attrs: noinline nounwind optnone uwtable
define dso_local i32 @main(i32 %0, i8** %1) #0 !dbg !23 {
  %3 = alloca i32, align 4
  %4 = alloca i8**, align 8
  %5 = alloca i32, align 4
  %6 = alloca i32, align 4
  store i32 %0, i32* %3, align 4
  call void @llvm.dbg.declare(metadata i32* %3, metadata !30, metadata !DIExpression()), !dbg !31
  store i8** %1, i8*** %4, align 8
  call void @llvm.dbg.declare(metadata i8*** %4, metadata !32, metadata !DIExpression()), !dbg !33
  call void @llvm.dbg.declare(metadata i32* %5, metadata !34, metadata !DIExpression()), !dbg !35
  %7 = load i8**, i8*** %4, align 8, !dbg !36
  %8 = getelementptr inbounds i8*, i8** %7, i64 1, !dbg !36
  %9 = load i8*, i8** %8, align 8, !dbg !36
  %10 = call i64 @atol(i8* %9) #4, !dbg !37
  %11 = trunc i64 %10 to i32, !dbg !37
  store i32 %11, i32* %5, align 4, !dbg !35
  call void @llvm.dbg.declare(metadata i32* %6, metadata !38, metadata !DIExpression()), !dbg !39
  %12 = load i8**, i8*** %4, align 8, !dbg !40
  %13 = getelementptr inbounds i8*, i8** %12, i64 2, !dbg !40
  %14 = load i8*, i8** %13, align 8, !dbg !40
  %15 = call i64 @atol(i8* %14) #4, !dbg !41
  %16 = trunc i64 %15 to i32, !dbg !41
  store i32 %16, i32* %6, align 4, !dbg !39
  %17 = load i32, i32* %5, align 4, !dbg !42
  %18 = load i32, i32* %6, align 4, !dbg !43
  %19 = call i32 @add(i32 %17, i32 %18), !dbg !44
  %20 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.str, i64 0, i64 0), i32 %19), !dbg !45
  ret i32 0, !dbg !46
}

; Function Attrs: nounwind readonly willreturn
declare dso_local i64 @atol(i8*) #2

declare dso_local i32 @printf(i8*, ...) #3

attributes #0 = { noinline nounwind optnone uwtable "disable-tail-calls"="false" "frame-pointer"="all" "less-precise-fpmad"="false" "min-legal-vector-width"="0" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { nofree nosync nounwind readnone speculatable willreturn }
attributes #2 = { nounwind readonly willreturn "disable-tail-calls"="false" "frame-pointer"="all" "less-precise-fpmad"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { "disable-tail-calls"="false" "frame-pointer"="all" "less-precise-fpmad"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="true" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+cx8,+fxsr,+mmx,+sse,+sse2,+x87" "tune-cpu"="generic" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #4 = { nounwind readonly willreturn }

!llvm.dbg.cu = !{!0}
!llvm.module.flags = !{!3, !4, !5}
!llvm.ident = !{!6}

!0 = distinct !DICompileUnit(language: DW_LANG_C99, file: !1, producer: "Ubuntu clang version 12.0.1-19ubuntu3", isOptimized: false, runtimeVersion: 0, emissionKind: FullDebug, enums: !2, splitDebugInlining: false, nameTableKind: None)
!1 = !DIFile(filename: "add.c", directory: "/home/franco/Funding/Galois/IC-2022/ElemCourse/src/lab5A")
!2 = !{}
!3 = !{i32 7, !"Dwarf Version", i32 4}
!4 = !{i32 2, !"Debug Info Version", i32 3}
!5 = !{i32 1, !"wchar_size", i32 4}
!6 = !{!"Ubuntu clang version 12.0.1-19ubuntu3"}
!7 = distinct !DISubprogram(name: "add", scope: !1, file: !1, line: 5, type: !8, scopeLine: 5, flags: DIFlagPrototyped, spFlags: DISPFlagDefinition, unit: !0, retainedNodes: !2)
!8 = !DISubroutineType(types: !9)
!9 = !{!10, !10, !10}
!10 = !DIDerivedType(tag: DW_TAG_typedef, name: "uint32_t", file: !11, line: 26, baseType: !12)
!11 = !DIFile(filename: "/usr/include/x86_64-linux-gnu/bits/stdint-uintn.h", directory: "")
!12 = !DIDerivedType(tag: DW_TAG_typedef, name: "__uint32_t", file: !13, line: 42, baseType: !14)
!13 = !DIFile(filename: "/usr/include/x86_64-linux-gnu/bits/types.h", directory: "")
!14 = !DIBasicType(name: "unsigned int", size: 32, encoding: DW_ATE_unsigned)
!15 = !DILocalVariable(name: "x", arg: 1, scope: !7, file: !1, line: 5, type: !10)
!16 = !DILocation(line: 5, column: 23, scope: !7)
!17 = !DILocalVariable(name: "y", arg: 2, scope: !7, file: !1, line: 5, type: !10)
!18 = !DILocation(line: 5, column: 35, scope: !7)
!19 = !DILocation(line: 6, column: 12, scope: !7)
!20 = !DILocation(line: 6, column: 16, scope: !7)
!21 = !DILocation(line: 6, column: 14, scope: !7)
!22 = !DILocation(line: 6, column: 5, scope: !7)
!23 = distinct !DISubprogram(name: "main", scope: !1, file: !1, line: 9, type: !24, scopeLine: 9, flags: DIFlagPrototyped, spFlags: DISPFlagDefinition, unit: !0, retainedNodes: !2)
!24 = !DISubroutineType(types: !25)
!25 = !{!26, !26, !27}
!26 = !DIBasicType(name: "int", size: 32, encoding: DW_ATE_signed)
!27 = !DIDerivedType(tag: DW_TAG_pointer_type, baseType: !28, size: 64)
!28 = !DIDerivedType(tag: DW_TAG_pointer_type, baseType: !29, size: 64)
!29 = !DIBasicType(name: "char", size: 8, encoding: DW_ATE_signed_char)
!30 = !DILocalVariable(name: "argc", arg: 1, scope: !23, file: !1, line: 9, type: !26)
!31 = !DILocation(line: 9, column: 15, scope: !23)
!32 = !DILocalVariable(name: "argv", arg: 2, scope: !23, file: !1, line: 9, type: !27)
!33 = !DILocation(line: 9, column: 28, scope: !23)
!34 = !DILocalVariable(name: "a", scope: !23, file: !1, line: 10, type: !10)
!35 = !DILocation(line: 10, column: 11, scope: !23)
!36 = !DILocation(line: 10, column: 20, scope: !23)
!37 = !DILocation(line: 10, column: 15, scope: !23)
!38 = !DILocalVariable(name: "b", scope: !23, file: !1, line: 11, type: !10)
!39 = !DILocation(line: 11, column: 11, scope: !23)
!40 = !DILocation(line: 11, column: 20, scope: !23)
!41 = !DILocation(line: 11, column: 15, scope: !23)
!42 = !DILocation(line: 12, column: 20, scope: !23)
!43 = !DILocation(line: 12, column: 22, scope: !23)
!44 = !DILocation(line: 12, column: 16, scope: !23)
!45 = !DILocation(line: 12, column: 2, scope: !23)
!46 = !DILocation(line: 13, column: 1, scope: !23)
