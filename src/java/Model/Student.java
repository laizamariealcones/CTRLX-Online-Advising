/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Model.Types.AccountStatus;
import Model.Types.PaymentStatus;
import Model.Types.RemarkType;
import Model.Types.RetentionStatus;
import Model.Types.StudentStatus;
import Model.Types.Term;
import Model.Types.YearStatus;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author edwar
 */
public class Student extends User {

    //Student variables
    private String program;
    private String block;
    private String courseID;
    private int currentUnits;
    private RetentionStatus retentionStatus;
    private AccountStatus accountStatus;
    private PaymentStatus paymentStatus;
    private StudentStatus status;
    private ArrayList<Course> backSubjects;
    private ArrayList<Course> studentSchedule;
    private ArrayList<CourseTaken> courseTaken;
    private Term term;
    private YearStatus year;

    //Error Message
    public static String advisingErrorMessage;

    //Database Connection
    static Connection conn;
    static PreparedStatement state;
    static ResultSet rs;

    static Connection test;
    static PreparedStatement pState;
    static ResultSet rSet;

    public Student() {
    }
    
    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public int getCurrentUnits() {
        return currentUnits;
    }

    public void setCurrentUnits(int currentUnits) {
        this.currentUnits = currentUnits;
    }

    public RetentionStatus getRetentionStatus() {
        return retentionStatus;
    }

    public void setRetentionStatus(RetentionStatus retentionStatus) {
        this.retentionStatus = retentionStatus;
    }
    
    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus status) {
        this.status = status;
    }

    public ArrayList<Course> getBackSubjects() {
        return backSubjects;
    }

    public void setBackSubjects(ArrayList<Course> backSubjects) {
        this.backSubjects = backSubjects;
    }

    public ArrayList<Course> getStudentSchedule() {
        return studentSchedule;
    }

    public void setStudentSchedule(ArrayList<Course> studentSchedule) {
        this.studentSchedule = studentSchedule;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public YearStatus getYear() {
        return year;
    }

    public void setYearStatus(YearStatus year) {
        this.year = year;
    }

    public static Student getStudent(int studentNumber, String password) {
        conn = DatabaseConnection.connectDatabase();
        String getStudentQuery = "select * from Student where Student_Id = (?) and password = (?)";
        Student student = new Student();

        try {
            state = conn.prepareStatement(getStudentQuery);
            state.setInt(1, studentNumber);
            state.setString(2, password);
            rs = state.executeQuery();

            if (rs.next()) {

                student.setUserID(studentNumber);
                student.setProgram(rs.getString("Program"));
                student.setCollege(rs.getString("College"));
                student.setBlock(rs.getString("Block"));
                student.setCurrentUnits(rs.getInt("Current_Units"));
//                student.setRetentionStatus(rs.getInt("Retention_Status"));
//                student.setStatus(rs.getInt("Status"));
                student.setFirstName(rs.getString("First_Name"));
                student.setLastName(rs.getString("Last_Name"));
                student.setMiddleName(rs.getString("Middle_Name"));
                student.setPassword(rs.getString("Password"));
                student.setEmail(rs.getString("Email"));
                student.setBirthdate(rs.getString("Birthdate"));
                student.setStudentSchedule(getStudentSchedule(studentNumber));                
                switch (rs.getInt("Term")) {
                    case 1:
                        student.setTerm(Term.First);
                        break;
                    case 2:
                        student.setYearStatus(YearStatus.Second);
                        break;
                    case 3:
                        student.setYearStatus(YearStatus.Third);
                        break;
                }
                switch (rs.getInt("Year")) {
                    case 1:
                        student.setYearStatus(YearStatus.First);
                        break;
                    case 2:
                        student.setYearStatus(YearStatus.Second);
                        break;
                    case 3:
                        student.setYearStatus(YearStatus.Third);
                        break;
                    case 4:
                        student.setYearStatus(YearStatus.Fourth);
                        break;
                    case 5:
                        student.setYearStatus(YearStatus.Fifth);
                        break;
                }
                student.setPicture(rs.getString("Student_Picture"));

                //Set Retention Status
                switch (rs.getInt("Retention_Status")) {
                    case 1:
                        student.setRetentionStatus(RetentionStatus.Regular);
                        break;
                    case 2:
                        student.setRetentionStatus(RetentionStatus.Irregular);
                        break;
                    case 3:
                        student.setRetentionStatus(RetentionStatus.Conditional);
                        break;
                    case 4:
                        student.setRetentionStatus(RetentionStatus.Debarred);
                        break;
                }

                //Set Student Status
                switch (rs.getInt("Status")) {
                    case 1:
                        student.setStatus(StudentStatus.OnGoing);
                        break;
                    case 2:
                        student.setStatus(StudentStatus.Completed);
                        break;
                    case 3:
                        student.setStatus(StudentStatus.Graduated);
                        break;
                    case 4:
                        student.setStatus(StudentStatus.Discontinued);
                        break;
                    case 5:
                        student.setStatus(StudentStatus.HonorableDi);
                        break;
                    case 6:
                        student.setStatus(StudentStatus.Cancelled);
                        break;
                    case 7:
                        student.setStatus(StudentStatus.OnGoing);
                        break;
                }
                
                //Set Account Status
                switch (rs.getInt("AccountStatus")) {
                    case 1:
                        student.setAccountStatus(AccountStatus.Active);
                        break;
                    case 2:
                        student.setAccountStatus(AccountStatus.Inactive);
                        break;
                }
            } else {
                return null;
            }
            rs.close();
            state.close();
            conn.close();
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Course> getCourseDeficiency(int studentID) {
        conn = DatabaseConnection.connectDatabase();
        ArrayList<Course> deficiencies = new ArrayList<>();
        String getCourseDeficiencyQuery = "select * from Student_Back_Subjects where Student_ID = (?)";

        try {
            state = conn.prepareStatement(getCourseDeficiencyQuery);
            state.setInt(1, studentID);
            rs = state.executeQuery();

            while (rs.next()) {
                Course courseDetails = Course.getCourseDetails(rs.getString("Course_ID"));
                deficiencies.add(courseDetails);
            }
            rs.close();
            state.close();
            conn.close();
            return deficiencies;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Adds a course to the student's schedule
    //Returns true if success; else return false
    public static boolean addToMyProposedSchedule(Course desiredCourse, Student currentStudent) {
        ArrayList<Course> schedule = currentStudent.getStudentSchedule();
        if (schedule.isEmpty() || isOverLapping(currentStudent, desiredCourse) == null) {
            currentStudent.getStudentSchedule().add(desiredCourse);

            conn = DatabaseConnection.connectDatabase();
            String getCourseDeficiencyQuery = "Insert into Student_Schedule(Student_ID, Course_ID, Section, Status) Values(?, ?, ?, ?)";
            String updateCourseQuery = "UPDATE Available_Courses SET AvailableSlot = (?) Where Course_ID = (?) and Section = (?)";
            int notYetSubmittedStatus = 4;

            try {
                //Adds Subject
                state = conn.prepareStatement(getCourseDeficiencyQuery);
                state.setInt(1, currentStudent.getUserID());
                state.setString(2, desiredCourse.getCourseID());
                state.setString(3, desiredCourse.getSection());
                state.setInt(4, notYetSubmittedStatus);
                state.executeUpdate();

                //Update Subject
                pState = conn.prepareStatement(updateCourseQuery);
                Course courseSlots = Course.getCourse(desiredCourse.getCourseID(), desiredCourse.getSection());
                pState.setInt(1, courseSlots.getAvailableSlot() - 1);
                pState.setString(2, desiredCourse.getCourseID());
                pState.setString(3, desiredCourse.getSection());
                pState.executeUpdate();

                rs.close();
                state.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;

    }

    public static ArrayList<Course> getStudentSchedule(int studentID) {
        test = DatabaseConnection.connectDatabase();
        ArrayList<Course> schedules = new ArrayList<>();
        String getStudentScheduleQuery = "select * from Student_Schedule where Student_ID = (?)";

        try {
            pState = test.prepareStatement(getStudentScheduleQuery);
            pState.setInt(1, studentID);
            rSet = pState.executeQuery();

            while (rSet.next()) {
                Course course = Course.getCourseDetails(rSet.getString("Course_ID"));
                course.setCourseID(rSet.getString("Course_ID"));
                course.setSection(rSet.getString("Section"));
                course.setSchedule(Course.getSchedules(rSet.getString("Course_ID"), rSet.getString("Section")));
                schedules.add(course);
            }
            rSet.close();
            pState.close();
            test.close();
            return schedules;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Course> getStudentPropSchedule(int studentID) {
        test = DatabaseConnection.connectDatabase();
        ArrayList<Course> schedules = new ArrayList<>();
        String getStudentScheduleQuery = "select * from Student_Schedule where Student_ID = (?) and (Status = 3 or Status = 4)";
        try {
            pState = test.prepareStatement(getStudentScheduleQuery);
            pState.setInt(1, studentID);
            rSet = pState.executeQuery();

            while (rSet.next()) {
                Course course = Course.getCourseDetails(rSet.getString("Course_ID"));
                course.setCourseID(rSet.getString("Course_ID"));
                course.setSection(rSet.getString("Section"));
                course.setSchedule(Course.getSchedules(rSet.getString("Course_ID"), rSet.getString("Section")));
                schedules.add(course);
            }
            rSet.close();
            pState.close();
            test.close();
            return schedules;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public static ArrayList<Course> getEnrolledSchedule(int studentID) {
        test = DatabaseConnection.connectDatabase();
        ArrayList<Course> schedules = new ArrayList<>();
        String getStudentScheduleQuery = "select * from Student_Schedule where Student_ID = (?) and Status = 1";
        try {
            pState = test.prepareStatement(getStudentScheduleQuery);
            pState.setInt(1, studentID);
            rSet = pState.executeQuery();

            while (rSet.next()) {
                Course course = Course.getCourseDetails(rSet.getString("Course_ID"));
                course.setCourseID(rSet.getString("Course_ID"));
                course.setSection(rSet.getString("Section"));
                course.setSchedule(Course.getSchedules(rSet.getString("Course_ID"), rSet.getString("Section")));
                schedules.add(course);
            }
            rSet.close();
            pState.close();
            test.close();
            return schedules;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Removes a course from the student's schedule
    public static void removeFromMySchedule(Student student, String courseID, String section) {

        conn = DatabaseConnection.connectDatabase();
        String removeCourseQuery = "DELETE FROM Student_Schedule WHERE Student_ID = (?) and Course_ID = (?)";
        String updateCourseQuery = "UPDATE Available_Courses SET AvailableSlot = (?) Where Course_ID = (?) and Section = (?)";
        System.out.println("Removing " + courseID);
        if (!Student.hasSubmittedSchedule(student.getUserID())) {
            try {
                //Removes Subject
                state = conn.prepareStatement(removeCourseQuery);
                state.setInt(1, student.getUserID());
                state.setString(2, courseID);
                state.executeUpdate();
                System.out.println("Successfully removed from the database");

                //Remove the Subject to Student Schedule
                ArrayList<Course> studentSchedule = student.getStudentSchedule();
                //The course to be removed
                Course removeCourse = Course.getCourse(courseID, section);
                System.out.println(removeCourse);
                for (Course studentCourse : studentSchedule) {
                    if (studentCourse.getCourseID().equals(removeCourse.getCourseID())) {
                        System.out.println(studentCourse.getCourseID());
                        studentSchedule.remove(studentCourse);
                        System.out.println("Course removed");
                        System.out.println("Successfully removed from the session");
                        break;
                    }
                }
                student.setStudentSchedule(studentSchedule);
                //Open Slot
                Course.openOneSlot(courseID, section);
                state.close();
//                pState.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("You cannot remove a subject you have already submitted");
        }
    }
    //Returns a specific course from the student's schedule

    //Resets schedule
    public static void resetSchedule(Student student) {
        conn = DatabaseConnection.connectDatabase();
        String resetCourse = "DELETE FROM Student_Schedule WHERE Student_ID = (?)";
        ArrayList<Course> studentSchedule = getStudentSchedule(student.getUserID());

        try {
            //Removes Subject
            state = conn.prepareStatement(resetCourse);
            state.setInt(1, student.getUserID());
            state.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Open slots
        for (Course studentCourse : studentSchedule) {
            System.out.println(studentCourse.getCourseID());
            Course.openOneSlot(studentCourse.getCourseID(), studentCourse.getSection());
        }
        studentSchedule.clear();
        student.setStudentSchedule(studentSchedule);

    }

    //Submits schedule
    public static void submitSchedule(int studentID) {
        conn = DatabaseConnection.connectDatabase();
        int status = 3;
        String updateStudentSchedStatus = "Update Student_Schedule set Status = (?) where Student_ID = (?)";

        if (!hasSubmittedSchedule(studentID)) {
            try {
                pState = conn.prepareStatement(updateStudentSchedStatus);
                pState.setInt(1, status);
                pState.setInt(2, studentID);
                pState.executeUpdate();

                state.close();
                pState.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Check if student has submitted a schedule
    public static boolean hasSubmittedSchedule(int studentID) {
        test = DatabaseConnection.connectDatabase();
        String hasSubmittedScheduleQuery = "select * from Student_Schedule where Student_ID = (?) and Status = (?)";
        int forApprovalStatus = 3;

        try {
            pState = test.prepareStatement(hasSubmittedScheduleQuery);
            pState.setInt(1, studentID);
            pState.setInt(2, forApprovalStatus);
            rSet = pState.executeQuery();
            if (rSet.next()) {
                System.out.println("Returns True");
                return true;
            }
            rSet.close();
            pState.close();
            test.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Check if student's schedule has been evaluated
    public static boolean hasScheduleEvaluated(int studentID) {
        test = DatabaseConnection.connectDatabase();
        String hasSubmittedScheduleQuery = "select * from Student_Schedule where Student_ID = (?) and (Status = 2 or Status = 5)";

        try {
            pState = test.prepareStatement(hasSubmittedScheduleQuery);
            pState.setInt(1, studentID);
            rSet = pState.executeQuery();
            if (rSet.next()) {
                System.out.println("Returns True");
                System.out.println("Sched true");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //get adviser's evaluation
    public static Evaluation getEvaluation(int studentID) {
        test = DatabaseConnection.connectDatabase();
        String hasSubmittedScheduleQuery = "select * from Student_Schedule where Student_ID = (?) and (Status = 2 or Status = 5)";
        int forApprovalStatus = 3;

        try {
            pState = test.prepareStatement(hasSubmittedScheduleQuery);
            pState.setInt(1, studentID);
            rSet = pState.executeQuery();
            if (rSet.next()) {
                Evaluation eval = new Evaluation();
                switch(rSet.getInt("Status")){
                    case 2:
                        eval.setEvaluation("APPROVED");
                        break;
                    case 5:
                        eval.setEvaluation("REJECTED");
                        break;
                }
                
                eval.setRemarks(rSet.getString("Remark"));
                return eval;
            }
            rSet.close();
            pState.close();
            test.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //Change password
    public static boolean changePassword(int studentID, String oldPassword, String newPassword) {
        conn = DatabaseConnection.connectDatabase();
        String checkPassword = "select Password from Student where Student_ID = (?)";
        String changePasswordQuery = "update Student set Password = (?) where Student_ID = (?) and Password = (?)";

        try {
            state = conn.prepareStatement(checkPassword);
            state.setInt(1, studentID);
            rs = state.executeQuery();
            if (rs.next()) {
                state = conn.prepareStatement(changePasswordQuery);
                state.setString(1, newPassword);
                state.setInt(2, studentID);
                state.setString(3, oldPassword);
                state.executeUpdate();
                return true;
            }
            rs.close();
            state.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static ArrayList<CourseTaken> getStudentGrades(int studentID){
        
        conn = DatabaseConnection.connectDatabase();
        ArrayList<CourseTaken> courseTaken = new ArrayList<>();
        
        String getStudentGrades = "Select * from Student_Grade where Student_ID = (?)";
        try{
            System.out.println("yahu");
            state = conn.prepareStatement(getStudentGrades);
            state.setInt(1, studentID);
            rs = state.executeQuery();
            
            while(rs.next()){
                CourseTaken studentGrade = new CourseTaken();
                studentGrade.setUserID(rs.getInt("Student_ID"));
                studentGrade.setCourseID(rs.getString("Course_ID"));
                System.out.println(studentGrade.getCourseID());
                studentGrade.setGrade(rs.getDouble("Course_Grade"));
                System.out.println(rs.getDouble("Course_Grade"));
                switch (rs.getInt("Year")) {
                    case 1:
                        studentGrade.setYearStatus(YearStatus.First);
                        break;
                    case 2:
                        studentGrade.setYearStatus(YearStatus.Second);
                        break;
                    case 3:
                        studentGrade.setYearStatus(YearStatus.Third);
                        break;
                    case 4:
                        studentGrade.setYearStatus(YearStatus.Fourth);
                        break;
                    case 5:
                        studentGrade.setYearStatus(YearStatus.Fifth);
                        break;
                }
                switch (rs.getInt("Remark")) {
                    case 1:
                        studentGrade.setRemarkType(RemarkType.PASSED);
                        break;
                    case 2:
                        studentGrade.setRemarkType(RemarkType.FAILED);
                        break;
                    case 3:
                        studentGrade.setRemarkType(RemarkType.INCOMPLETE);
                        break;
                }
                
                System.out.println("Course about to add: " + studentGrade.getCourseID() + " with grade + " + studentGrade.getGrade() );
                courseTaken.add(studentGrade);
                //proposedSchedList.add(rs.getInt("Student_ID"));
            }
            for(CourseTaken course : courseTaken){
                System.out.println("Course ID: " + course.getCourseID());
            }
            rs.close();
            state.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return courseTaken;
    }

    public static int getScheduleTotalUnits(Student student) {
        int totalUnits = 0;
        if (student.getStudentSchedule().isEmpty()) {
            totalUnits = 0;
        } else {
            for (Course course : student.getStudentSchedule()) {
                totalUnits += course.getCourseLectUnits();
                totalUnits += course.getCourseLabUnits();
            }
        }
        System.out.println("Total units " + totalUnits);

        return totalUnits;
    }

    //Check if overlapping
    //Returns true if overlapping, else false
    public static Course isOverLapping(Student student, Course desiredCourse) {

        System.out.println("Check overlapping");
        SimpleDateFormat parser = new SimpleDateFormat("H:mm a");
        System.out.println(desiredCourse.getCourseID());
        System.out.println(desiredCourse.getSection());

        ArrayList<Course> studentSchedule = student.getStudentSchedule();
        ArrayList<CourseSchedule> desiredCourseSchedule = Course.getSchedules(desiredCourse.getCourseID(), desiredCourse.getSection());

        for (Course c : studentSchedule) {

            try {

                //Student's Current Schedule
                ArrayList<CourseSchedule> studentCourseSchedule = Course.getSchedules(c.getCourseID(), c.getSection());

                //Desired Course Time
                for (CourseSchedule sc : studentCourseSchedule) {
                    Date studentCourseStartTime = parser.parse(sc.getStartTime());
                    System.out.println(studentCourseStartTime);
                    Date studentCourseEndTime = parser.parse(sc.getEndTime());
                    System.out.println(studentCourseEndTime);
                    String studentCourseDay = sc.getDay();
                    System.out.println(studentCourseDay);

                    for (CourseSchedule desiredSched : desiredCourseSchedule) {
                        Date desiredCourseStartTime = parser.parse(desiredSched.getStartTime());
                        System.out.println(desiredCourseStartTime);
                        Date desiredCourseEndTime = parser.parse(desiredSched.getEndTime());
                        System.out.println(desiredCourseEndTime);

                        //Check if student is already enlisted in the same course from a different block
                        /*System.out.println("C: " + c.getCourseID());
                        System.out.println("D: " + desiredCourse.getCourseID());*/
                        if (c.getCourseID().equals(desiredCourse.getCourseID())) {
                            advisingErrorMessage = "Already in the same subject";
                            System.out.println("Already in the same subject");
                            return c;
                        }
                        //First check; if it has the same time 
                        if (desiredCourseEndTime.equals(studentCourseEndTime) && desiredCourseStartTime.equals(studentCourseStartTime) && desiredSched.getDay().equals(sc.getDay())) {
                            advisingErrorMessage = "has the same time ";
                            System.out.println("has the same time ");
                            return c;
                        }
                        //Second check; if it starts before and ends during another course
                        if (desiredCourseStartTime.before(studentCourseStartTime) && desiredCourseEndTime.before(studentCourseEndTime) && desiredSched.getDay().equals(sc.getDay())) {
                            advisingErrorMessage = "starts before and ends during another course";
                            System.out.println("starts before and ends during another course");
                            return c;
                        }
                        //Third Check; if it starts before a course and ends at the same time
                        if (desiredCourseStartTime.before(studentCourseStartTime) && desiredCourseEndTime.equals(studentCourseEndTime) && desiredSched.getDay().equals(sc.getDay())) {
                            advisingErrorMessage = "starts before a course and ends at the same time";
                            System.out.println("starts before a course and ends at the same time");
                            return c;
                        }
//                        //Fourth Check; if it starts after a course and ends during the course
//                        if (desiredCourseStartTime.after(studentCourseStartTime) && desiredCourseEndTime.before(studentCourseEndTime) && desiredSched.getDay().equals(sc.getDay())) {
//                            advisingErrorMessage = "starts after a course and ends during the course";
//                            System.out.println("starts after a course and ends during the course");
//                            System.out.println(desiredCourseStartTime.after(studentCourseStartTime));
//                            System.out.println(desiredCourseEndTime.before(studentCourseEndTime));
//                            System.out.println("Desired Course End Time: " + desiredCourseEndTime);
//                            System.out.println("Student Course end time: " + studentCourseEndTime);
//                            System.out.println("");
//                            return false;
//                        }
                        //Fifth Check; if it starts after a course and ends the same time with the course
                        if (desiredCourseStartTime.after(studentCourseStartTime) && desiredCourseEndTime.equals(studentCourseEndTime) && desiredSched.getDay().equals(sc.getDay())) {
                            advisingErrorMessage = "starts after a course and ends the same time with the course";
                            System.out.println("starts after a course and ends the same time with the course");
                            return c;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
