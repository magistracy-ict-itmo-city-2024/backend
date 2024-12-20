/*
 * This file is generated by jOOQ.
 */
package ru.citycheck.core.domain.db.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record13;
import org.jooq.Row13;
import org.jooq.impl.UpdatableRecordImpl;

import ru.citycheck.core.domain.db.tables.Issue;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class IssueRecord extends UpdatableRecordImpl<IssueRecord> implements Record13<Long, String, String, String, Long, Long, Long, Long, String, Double, Double, Long, Long> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.issue.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.issue.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.issue.description</code>.
     */
    public void setDescription(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.issue.description</code>.
     */
    public String getDescription() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.issue.status</code>.
     */
    public void setStatus(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.issue.status</code>.
     */
    public String getStatus() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.issue.priority</code>.
     */
    public void setPriority(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.issue.priority</code>.
     */
    public String getPriority() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.issue.category_id</code>.
     */
    public void setCategoryId(Long value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.issue.category_id</code>.
     */
    public Long getCategoryId() {
        return (Long) get(4);
    }

    /**
     * Setter for <code>public.issue.assignee_id</code>.
     */
    public void setAssigneeId(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.issue.assignee_id</code>.
     */
    public Long getAssigneeId() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.issue.created_at</code>.
     */
    public void setCreatedAt(Long value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.issue.created_at</code>.
     */
    public Long getCreatedAt() {
        return (Long) get(6);
    }

    /**
     * Setter for <code>public.issue.updated_at</code>.
     */
    public void setUpdatedAt(Long value) {
        set(7, value);
    }

    /**
     * Getter for <code>public.issue.updated_at</code>.
     */
    public Long getUpdatedAt() {
        return (Long) get(7);
    }

    /**
     * Setter for <code>public.issue.actuality_status</code>.
     */
    public void setActualityStatus(String value) {
        set(8, value);
    }

    /**
     * Getter for <code>public.issue.actuality_status</code>.
     */
    public String getActualityStatus() {
        return (String) get(8);
    }

    /**
     * Setter for <code>public.issue.location_lat</code>.
     */
    public void setLocationLat(Double value) {
        set(9, value);
    }

    /**
     * Getter for <code>public.issue.location_lat</code>.
     */
    public Double getLocationLat() {
        return (Double) get(9);
    }

    /**
     * Setter for <code>public.issue.location_lon</code>.
     */
    public void setLocationLon(Double value) {
        set(10, value);
    }

    /**
     * Getter for <code>public.issue.location_lon</code>.
     */
    public Double getLocationLon() {
        return (Double) get(10);
    }

    /**
     * Setter for <code>public.issue.document_id</code>.
     */
    public void setDocumentId(Long value) {
        set(11, value);
    }

    /**
     * Getter for <code>public.issue.document_id</code>.
     */
    public Long getDocumentId() {
        return (Long) get(11);
    }

    /**
     * Setter for <code>public.issue.reporter_id</code>.
     */
    public void setReporterId(Long value) {
        set(12, value);
    }

    /**
     * Getter for <code>public.issue.reporter_id</code>.
     */
    public Long getReporterId() {
        return (Long) get(12);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record13 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row13<Long, String, String, String, Long, Long, Long, Long, String, Double, Double, Long, Long> fieldsRow() {
        return (Row13) super.fieldsRow();
    }

    @Override
    public Row13<Long, String, String, String, Long, Long, Long, Long, String, Double, Double, Long, Long> valuesRow() {
        return (Row13) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return Issue.ISSUE.ID;
    }

    @Override
    public Field<String> field2() {
        return Issue.ISSUE.DESCRIPTION;
    }

    @Override
    public Field<String> field3() {
        return Issue.ISSUE.STATUS;
    }

    @Override
    public Field<String> field4() {
        return Issue.ISSUE.PRIORITY;
    }

    @Override
    public Field<Long> field5() {
        return Issue.ISSUE.CATEGORY_ID;
    }

    @Override
    public Field<Long> field6() {
        return Issue.ISSUE.ASSIGNEE_ID;
    }

    @Override
    public Field<Long> field7() {
        return Issue.ISSUE.CREATED_AT;
    }

    @Override
    public Field<Long> field8() {
        return Issue.ISSUE.UPDATED_AT;
    }

    @Override
    public Field<String> field9() {
        return Issue.ISSUE.ACTUALITY_STATUS;
    }

    @Override
    public Field<Double> field10() {
        return Issue.ISSUE.LOCATION_LAT;
    }

    @Override
    public Field<Double> field11() {
        return Issue.ISSUE.LOCATION_LON;
    }

    @Override
    public Field<Long> field12() {
        return Issue.ISSUE.DOCUMENT_ID;
    }

    @Override
    public Field<Long> field13() {
        return Issue.ISSUE.REPORTER_ID;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getDescription();
    }

    @Override
    public String component3() {
        return getStatus();
    }

    @Override
    public String component4() {
        return getPriority();
    }

    @Override
    public Long component5() {
        return getCategoryId();
    }

    @Override
    public Long component6() {
        return getAssigneeId();
    }

    @Override
    public Long component7() {
        return getCreatedAt();
    }

    @Override
    public Long component8() {
        return getUpdatedAt();
    }

    @Override
    public String component9() {
        return getActualityStatus();
    }

    @Override
    public Double component10() {
        return getLocationLat();
    }

    @Override
    public Double component11() {
        return getLocationLon();
    }

    @Override
    public Long component12() {
        return getDocumentId();
    }

    @Override
    public Long component13() {
        return getReporterId();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getDescription();
    }

    @Override
    public String value3() {
        return getStatus();
    }

    @Override
    public String value4() {
        return getPriority();
    }

    @Override
    public Long value5() {
        return getCategoryId();
    }

    @Override
    public Long value6() {
        return getAssigneeId();
    }

    @Override
    public Long value7() {
        return getCreatedAt();
    }

    @Override
    public Long value8() {
        return getUpdatedAt();
    }

    @Override
    public String value9() {
        return getActualityStatus();
    }

    @Override
    public Double value10() {
        return getLocationLat();
    }

    @Override
    public Double value11() {
        return getLocationLon();
    }

    @Override
    public Long value12() {
        return getDocumentId();
    }

    @Override
    public Long value13() {
        return getReporterId();
    }

    @Override
    public IssueRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public IssueRecord value2(String value) {
        setDescription(value);
        return this;
    }

    @Override
    public IssueRecord value3(String value) {
        setStatus(value);
        return this;
    }

    @Override
    public IssueRecord value4(String value) {
        setPriority(value);
        return this;
    }

    @Override
    public IssueRecord value5(Long value) {
        setCategoryId(value);
        return this;
    }

    @Override
    public IssueRecord value6(Long value) {
        setAssigneeId(value);
        return this;
    }

    @Override
    public IssueRecord value7(Long value) {
        setCreatedAt(value);
        return this;
    }

    @Override
    public IssueRecord value8(Long value) {
        setUpdatedAt(value);
        return this;
    }

    @Override
    public IssueRecord value9(String value) {
        setActualityStatus(value);
        return this;
    }

    @Override
    public IssueRecord value10(Double value) {
        setLocationLat(value);
        return this;
    }

    @Override
    public IssueRecord value11(Double value) {
        setLocationLon(value);
        return this;
    }

    @Override
    public IssueRecord value12(Long value) {
        setDocumentId(value);
        return this;
    }

    @Override
    public IssueRecord value13(Long value) {
        setReporterId(value);
        return this;
    }

    @Override
    public IssueRecord values(Long value1, String value2, String value3, String value4, Long value5, Long value6, Long value7, Long value8, String value9, Double value10, Double value11, Long value12, Long value13) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        value12(value12);
        value13(value13);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached IssueRecord
     */
    public IssueRecord() {
        super(Issue.ISSUE);
    }

    /**
     * Create a detached, initialised IssueRecord
     */
    public IssueRecord(Long id, String description, String status, String priority, Long categoryId, Long assigneeId, Long createdAt, Long updatedAt, String actualityStatus, Double locationLat, Double locationLon, Long documentId, Long reporterId) {
        super(Issue.ISSUE);

        setId(id);
        setDescription(description);
        setStatus(status);
        setPriority(priority);
        setCategoryId(categoryId);
        setAssigneeId(assigneeId);
        setCreatedAt(createdAt);
        setUpdatedAt(updatedAt);
        setActualityStatus(actualityStatus);
        setLocationLat(locationLat);
        setLocationLon(locationLon);
        setDocumentId(documentId);
        setReporterId(reporterId);
    }
}
