package org.jasig.ssp.util;

import com.google.common.collect.Maps;
import org.jasig.ssp.model.*;
import org.jasig.ssp.transferobject.*;
import org.jasig.ssp.model.external.FacultyCourse;
import org.jasig.ssp.model.external.Term;
import org.jasig.ssp.model.reference.EarlyAlertOutcome;
import org.jasig.ssp.transferobject.messagetemplate.*;
import org.jasig.ssp.transferobject.reference.*;
import org.jasig.ssp.transferobject.reports.MapStatusReportSummaryDetail;
import org.jasig.ssp.util.collections.Pair;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.*;

/**
 * MessageTemplatePreviewTO Builder creates transition objects used when building Message Templates for the preview.
 *
 * Created by msultzaberger on 6/8/2015.
 */
public class MessageTemplatePreviewTOBuilder {

    public static Person createPerson() {
        return createPerson("");
    }

    public static Person createPerson(final boolean addCoach) {
        return createPerson("", addCoach);
    }

    public static Person createPerson(final String suffix) {
        return createPerson(suffix, true);
    }
    public static Person createPerson(final String suffix, final boolean addCoach) {
        Person person = new Person();
        setSetters(person, suffix);
        if (addCoach) {
            person.setCoach(createPerson(suffix + "_Coach", false));
        }
        return person;
    }

    public static List<TaskTO> createTaskTOList () {
        List<TaskTO> list = new ArrayList<TaskTO>();
        list.add(createTaskTO("_Task1"));
        list.add(createTaskTO("_Task2"));
        list.add(createTaskTO("_Task3"));
        return list;
    }

    public static TaskTO createTaskTO () {
        return createTaskTO("");
    }

    public static TaskTO createTaskTO (String suffix) {
        TaskTO taskTO = new TaskTO();
        setSetters(taskTO, suffix);
        return taskTO;
    }

    public static List<GoalTO> createGoalTOList () {
        List<GoalTO> list = new ArrayList<GoalTO>();
        list.add(createGoalTO("_Goal1"));
        list.add(createGoalTO("_Goal2"));
        list.add(createGoalTO("_Goal3"));
        return list;
    }

    public static GoalTO createGoalTO () {
        return createGoalTO("");
    }

    public static GoalTO createGoalTO (String suffix) {
        GoalTO goalTO = new GoalTO();
        setSetters(goalTO, suffix);
        return goalTO;
    }

    public static List<StrengthTO> createStrengthTOList () {
        List<StrengthTO> list = new ArrayList<StrengthTO>();
        list.add(createStrengthTO("_Strength1"));
        list.add(createStrengthTO("_Strength2"));
        list.add(createStrengthTO("_Strength3"));
        return list;
    }

    public static StrengthTO createStrengthTO () {
        return createStrengthTO("");
    }

    public static StrengthTO createStrengthTO (String suffix) {
        StrengthTO strengthTO = new StrengthTO();
        setSetters(strengthTO, suffix);
        return strengthTO;
    }

    public static StudentPersonLiteMessageTemplateTO createStudentPersonLiteMessageTemplateTO() {
        return createStudentPersonLiteMessageTemplateTO("");
    }

    public static StudentPersonLiteMessageTemplateTO createStudentPersonLiteMessageTemplateTO(final String suffix) {
        StudentPersonLiteMessageTemplateTO studentPersonLiteMessageTemplateTO = new StudentPersonLiteMessageTemplateTO(createPerson(suffix));
        setSetters(studentPersonLiteMessageTemplateTO, suffix);
        return studentPersonLiteMessageTemplateTO;
    }

    public static CoachPersonLiteMessageTemplateTO createCoachPersonLiteMessageTemplateTO() {
        return createCoachPersonLiteMessageTemplateTO("");
    }

    public static CoachPersonLiteMessageTemplateTO createCoachPersonLiteMessageTemplateTO(final String suffix) {
        CoachPersonLiteMessageTemplateTO coachPersonLiteMessageTemplateTO = new CoachPersonLiteMessageTemplateTO(createPerson(suffix, false));
        setSetters(coachPersonLiteMessageTemplateTO, suffix);
        return coachPersonLiteMessageTemplateTO;
    }

    public static TaskMessageTemplateTO createTaskMessageTemplateTO() {
        return createTaskMessageTemplateTO("");
    }

    public static TaskMessageTemplateTO createTaskMessageTemplateTO(final String suffix) {
        TaskMessageTemplateTO taskMessageTemplateTO = new TaskMessageTemplateTO();
        setSetters(taskMessageTemplateTO, suffix);
        taskMessageTemplateTO.setPerson(MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO("_person"));
        taskMessageTemplateTO.setCoach(MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO("_coach"));
        taskMessageTemplateTO.setCreator(MessageTemplatePreviewTOBuilder.createCoachPersonLiteMessageTemplateTO("_creator"));
        return taskMessageTemplateTO;
    }

    public static EarlyAlertMessageTemplateTO createEarlyAlertMessageTemplateTO() {
        return createEarlyAlertMessageTemplateTO("");
    }

    public static EarlyAlertMessageTemplateTO createEarlyAlertMessageTemplateTO(final String suffix) {
        EarlyAlertMessageTemplateTO earlyAlertMessageTemplateTO = new EarlyAlertMessageTemplateTO(createEarlyAlert(suffix), createPerson(suffix));
        earlyAlertMessageTemplateTO.setCoach(createCoachPersonLiteMessageTemplateTO("_coach" + suffix));
        earlyAlertMessageTemplateTO.setCreatedBy(createPersonLiteTO("_createdBy" + suffix));
        earlyAlertMessageTemplateTO.setPerson(createStudentPersonLiteMessageTemplateTO("_student" + suffix));
        Set<String> watcherEmails = new HashSet<String>();
        watcherEmails.add("fake_1@email.com");
        watcherEmails.add("fake_2@email.com");
        watcherEmails.add("fake_3@email.com");
        earlyAlertMessageTemplateTO.setWatcherEmails(watcherEmails);
        earlyAlertMessageTemplateTO.setCampus(createCampusTO(suffix));
        earlyAlertMessageTemplateTO.setEarlyAlertReasonTOs(createEarlyAlertReasonTOs(suffix));
        earlyAlertMessageTemplateTO.setEarlyAlertSuggestionTOs(createEarlyAlertSuggestionTOs(suffix));
        return earlyAlertMessageTemplateTO;
    }

    public static Set<EarlyAlertReasonTO> createEarlyAlertReasonTOs(final String suffix) {
        Set<EarlyAlertReasonTO> earlyAlertReasonTOs = new HashSet<EarlyAlertReasonTO>();
        earlyAlertReasonTOs.add(createEarlyAlertReasonTO(suffix + "_Reason1"));
        earlyAlertReasonTOs.add(createEarlyAlertReasonTO(suffix + "_Reason2"));
        earlyAlertReasonTOs.add(createEarlyAlertReasonTO(suffix + "_Reason3"));
        return earlyAlertReasonTOs;
    }

    public static EarlyAlertReasonTO createEarlyAlertReasonTO(final String suffix) {
        EarlyAlertReasonTO earlyAlertReasonTO = new EarlyAlertReasonTO();
        setSetters(earlyAlertReasonTO, suffix);
        return earlyAlertReasonTO;
    }

    public static Set<EarlyAlertSuggestionTO> createEarlyAlertSuggestionTOs(final String suffix) {
        Set<EarlyAlertSuggestionTO> earlyAlertSuggestionTOs = new HashSet<EarlyAlertSuggestionTO>();
        earlyAlertSuggestionTOs.add(createEarlyAlertSuggestionTO(suffix + "_Suggestion1"));
        earlyAlertSuggestionTOs.add(createEarlyAlertSuggestionTO(suffix + "_Suggestion2"));
        earlyAlertSuggestionTOs.add(createEarlyAlertSuggestionTO(suffix + "_Suggestion3"));
        return earlyAlertSuggestionTOs;
    }

    public static EarlyAlertSuggestionTO createEarlyAlertSuggestionTO(final String suffix) {
        EarlyAlertSuggestionTO earlyAlertSuggestionTO = new EarlyAlertSuggestionTO();
        setSetters(earlyAlertSuggestionTO, suffix);
        return earlyAlertSuggestionTO;
    }

    public static CampusTO createCampusTO(final String suffix) {
        CampusTO campusTO = new CampusTO();
        setSetters(campusTO, "_campus" + suffix);
        return campusTO;
    }

    public static EarlyAlertResponseMessageTemplateTO createEarlyAlertResponseMessageTemplateTO() {
        EarlyAlertResponseMessageTemplateTO earlyAlertResponseMessageTemplateTO = new EarlyAlertResponseMessageTemplateTO();
        setSetters(earlyAlertResponseMessageTemplateTO,"");
        earlyAlertResponseMessageTemplateTO.setCreator(createCoachPersonLiteMessageTemplateTO("_creator"));

        earlyAlertResponseMessageTemplateTO.setEarlyAlertOutcome(createEarlyAlertOutcomeTO());

        List<EarlyAlertOutreachTO> earlyAlertOutreachTOs = new ArrayList<EarlyAlertOutreachTO>();
        earlyAlertOutreachTOs.add(createEarlyAlertOutreachTO("_OutReach1"));
        earlyAlertOutreachTOs.add(createEarlyAlertOutreachTO("_OutReach2"));
        earlyAlertOutreachTOs.add(createEarlyAlertOutreachTO("_OutReach3"));
        earlyAlertResponseMessageTemplateTO.setEarlyAlertOutreach(earlyAlertOutreachTOs);

        List<EarlyAlertReferralTO> earlyAlertReferralTOs = new ArrayList<EarlyAlertReferralTO>();
        earlyAlertReferralTOs.add(createEarlyAlertReferralTO("_Referral1"));
        earlyAlertReferralTOs.add(createEarlyAlertReferralTO("_Referral2"));
        earlyAlertReferralTOs.add(createEarlyAlertReferralTO("_Referral3"));
        earlyAlertResponseMessageTemplateTO.setEarlyAlertReferrals(earlyAlertReferralTOs);

        return earlyAlertResponseMessageTemplateTO;
    }

    public static EarlyAlertOutcomeTO createEarlyAlertOutcomeTO() {
        EarlyAlertOutcomeTO earlyAlertOutcomeTO = new EarlyAlertOutcomeTO();
        setSetters(earlyAlertOutcomeTO,"");
        return earlyAlertOutcomeTO;
    }

    public static EarlyAlertOutreachTO createEarlyAlertOutreachTO(final String suffix) {
        EarlyAlertOutreachTO earlyAlertOutreachTO = new EarlyAlertOutreachTO();
        setSetters(earlyAlertOutreachTO,suffix);
        return earlyAlertOutreachTO;
    }

    public static EarlyAlertReferralTO createEarlyAlertReferralTO(final String suffix) {
        EarlyAlertReferralTO earlyAlertReferralTO = new EarlyAlertReferralTO();
        setSetters(earlyAlertReferralTO,suffix);
        return earlyAlertReferralTO;
    }

    public static EarlyAlert createEarlyAlert() {
        return createEarlyAlert("");
    }

    public static EarlyAlert createEarlyAlert(final String suffix) {
        EarlyAlert earlyAlert = new EarlyAlert();
        setSetters(earlyAlert, suffix);
        earlyAlert.setPerson(createPerson(suffix));
        earlyAlert.setCreatedBy(createAuditPerson(suffix));
        return earlyAlert;
    }

    public static AuditPerson createAuditPerson() {
        return createAuditPerson("");
    }

    public static AuditPerson createAuditPerson(final String suffix) {
        AuditPerson auditPerson = new AuditPerson();
        setSetters(auditPerson, suffix);
        return auditPerson;
    }

    public static EarlyAlertOutcomeMessageTemplateTO createEarlyAlertOutcomeMessageTemplateTO() {
        EarlyAlertOutcomeMessageTemplateTO earlyAlertOutcomeMessageTemplateTO = new EarlyAlertOutcomeMessageTemplateTO();
        setSetters(earlyAlertOutcomeMessageTemplateTO, "");
        return earlyAlertOutcomeMessageTemplateTO;
    }

    public static FacultyCourse createFacultyCourse() {
        return createFacultyCourse("");
    }

    public static FacultyCourse createFacultyCourse(final String suffix) {
        FacultyCourse facultyCourse = new FacultyCourse();
        setSetters(facultyCourse, suffix);
        return facultyCourse;
    }

    public static Term createTerm() {
        return createTerm("");
    }

    public static Term createTerm(final String suffix) {
        Term term = new Term();
        setSetters(term, suffix);
        return term;
    }

    public static List<Pair<EarlyAlertMessageTemplateTO,Integer>> createEarlyAlertTOPairs() {
        List<Pair<EarlyAlertMessageTemplateTO,Integer>> earlyAlertTOPairs = new ArrayList<Pair<EarlyAlertMessageTemplateTO,Integer>>();
        earlyAlertTOPairs.add(new Pair<EarlyAlertMessageTemplateTO, Integer>(createEarlyAlertMessageTemplateTO("_1"),new Integer(1)));
        earlyAlertTOPairs.add(new Pair<EarlyAlertMessageTemplateTO, Integer>(createEarlyAlertMessageTemplateTO("_2"),new Integer(2)));
        earlyAlertTOPairs.add(new Pair<EarlyAlertMessageTemplateTO, Integer>(createEarlyAlertMessageTemplateTO("_3"),new Integer(3)));
        return earlyAlertTOPairs;
    }

    public static MessageTO createMessageTO() {
        MessageTO messageTO = new MessageTO();
        setSetters(messageTO,"");
        messageTO.setSender(createPersonLiteTO());
        return messageTO;
    }

    public static LinkedHashMap<Object, Object> createMessageContextTO() {
        final LinkedHashMap<Object, Object> messageContextTO = Maps.newLinkedHashMap();
        messageContextTO.put("originalBody", "This is the original email body.");
        return messageContextTO;
    }

    public static PersonLiteTO createPersonLiteTO() {
        return createPersonLiteTO("");
    }
    public static PersonLiteTO createPersonLiteTO(final String suffix) {
        PersonLiteTO personLiteTO = new PersonLiteTO();
        setSetters(personLiteTO, suffix);
        return personLiteTO;
    }

    public static PlanTO createPlanTO() {
        PlanTO planTO = new PlanTO();
        setSetters(planTO, "");
        return planTO;
    }

    public static List<TermCourses<Plan, PlanTO>> createTermCourses() {
        List<TermCourses<Plan, PlanTO>> termCourses = new ArrayList<TermCourses<Plan, PlanTO>>();
        termCourses.add(createTermCourse("_1"));
        termCourses.add(createTermCourse("_2"));
        termCourses.add(createTermCourse("_3"));
        return termCourses;
    }

    public static TermCourses createTermCourse(final String suffix) {
        TermCourses termCourses = new TermCourses(createTerm(suffix));
        setSetters(termCourses,suffix);
        termCourses.addCourse(createPlanCourseTO(suffix + "_1"));
        termCourses.addCourse(createPlanCourseTO(suffix + "_2"));
        termCourses.addCourse(createPlanCourseTO(suffix + "_3"));
        termCourses.setTerm(createTerm(suffix));
        return termCourses;
    }

    public static PlanCourseTO createPlanCourseTO(final String suffix) {
        PlanCourseTO planCourseTO = new PlanCourseTO();
        setSetters(planCourseTO, suffix);
        planCourseTO.setIsDev(true);
        return planCourseTO;
    }

    public static List<MapStatusReportSummaryDetail> createMapStatusReportSummaryDetails() {
        List<MapStatusReportSummaryDetail> mapStatusReportSummaryDetails = new ArrayList<MapStatusReportSummaryDetail>();
        mapStatusReportSummaryDetails.add(createMapStatusReportSummaryDetail("_1", 1L));
        mapStatusReportSummaryDetails.add(createMapStatusReportSummaryDetail("_2", 2L));
        mapStatusReportSummaryDetails.add(createMapStatusReportSummaryDetail("_3", 3L));
        return mapStatusReportSummaryDetails;
    }

    public static MapStatusReportSummaryDetail createMapStatusReportSummaryDetail(final String suffix, Long count) {
        MapStatusReportSummaryDetail mapStatusReportSummaryDetail = new MapStatusReportSummaryDetail("PlanName" + suffix, count);
        setSetters(mapStatusReportSummaryDetail, suffix);
        return mapStatusReportSummaryDetail;
    }

    private static void setSetters(Object o, final String suffix) {
        for (Method m : o.getClass().getMethods()) {
            String methodName = m.getName();
            if (methodName.startsWith("set") && m.getParameterTypes().length == 1) {
                try {
                    String parameterType = m.getParameterTypes()[0].getName();
                    if (parameterType.equals("java.lang.String")) {
                        m.invoke(o, methodName.substring(3) + suffix);
                    } else if (parameterType.equals("java.util.Date")) {
                        m.invoke(o, Calendar.getInstance().getTime());
                    } else if (parameterType.equals("java.math.BigDecimal")) {
                        m.invoke(o, new BigDecimal(11));
                    } else if (parameterType.equals("java.Lang.Float")) {
                        m.invoke(o, new Float(22.2));
                    } else if (parameterType.equals("java.Lang.Integer")) {
                        m.invoke(o, new Integer(33));
                    } else if (parameterType.equals("java.Lang.Double")) {
                        m.invoke(o, new Double(44));
                    } else if (parameterType.equals("java.Lang.Boolean")) {
                        m.invoke(o, new Boolean(true));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}