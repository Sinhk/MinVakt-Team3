/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import java.io.Serializable;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.9.0"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
@Entity
@Table(name = "department", schema = "g_scrum03")
public class Department implements Serializable {

    private static final long serialVersionUID = -701733457;

    private Short  departmentId;
    private String departmentName;

    public Department() {}

    public Department(Department value) {
        this.departmentId = value.departmentId;
        this.departmentName = value.departmentName;
    }

    public Department(
        Short  departmentId,
        String departmentName
    ) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", unique = true, nullable = false, precision = 5)
    public Short getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Short departmentId) {
        this.departmentId = departmentId;
    }

    @Column(name = "department_name", nullable = false, length = 30)
    public String getDepartmentName() {
        return this.departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Department (");

        sb.append(departmentId);
        sb.append(", ").append(departmentName);

        sb.append(")");
        return sb.toString();
    }
}
