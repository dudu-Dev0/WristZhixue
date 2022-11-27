package com.dudu.wristzhixue.util

class Urls {
    companion object {
        const val student = Constants.apiServer + "student"   //学生信息
        const val clazz = "$student/clazz"    //学生班级信息
        const val allClazz = "$student/clazzs"    //全校班级
        const val classmates = "$student/classmates"  //同班同学
        const val exam = "$student/exam"     //考试信息
        const val allExam = "$student/exams"      //全部考试信息
        const val mark = "$student/mark"      //考试分数
        const val allSubject = "$student/allSubject"      //考试列表
        const val getSubjectDiagnosis = "$student/getSubjectDiagnosis"    //考试诊断
    }
}