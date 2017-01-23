/*
 * This file is generated by jOOQ.
*/
package minvakt.datamodel.tables.pojos;


import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;


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
@Table(name = "employee_category", schema = "g_scrum03")
public class EmployeeCategory implements Serializable {

    private static final long serialVersionUID = -990278417;

    private Short   categoryId;
    private String  categoryName;
    private Boolean admin;
    private Short   requiredPerShift;
    private Boolean availableForShifts;

    public EmployeeCategory() {}

    public EmployeeCategory(EmployeeCategory value) {
        this.categoryId = value.categoryId;
        this.categoryName = value.categoryName;
        this.admin = value.admin;
        this.requiredPerShift = value.requiredPerShift;
        this.availableForShifts = value.availableForShifts;
    }

    public EmployeeCategory(
        Short   categoryId,
        String  categoryName,
        Boolean admin,
        Short   requiredPerShift,
        Boolean availableForShifts
    ) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.admin = admin;
        this.requiredPerShift = requiredPerShift;
        this.availableForShifts = availableForShifts;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", unique = true, nullable = false, precision = 5)
    public Short getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(Short categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "category_name", nullable = false, length = 30)
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Column(name = "admin")
    public Boolean getAdmin() {
        return this.admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Column(name = "required_per_shift", precision = 5)
    public Short getRequiredPerShift() {
        return this.requiredPerShift;
    }

    public void setRequiredPerShift(Short requiredPerShift) {
        this.requiredPerShift = requiredPerShift;
    }

    @Column(name = "available_for_shifts")
    public Boolean getAvailableForShifts() {
        return this.availableForShifts;
    }

    public void setAvailableForShifts(Boolean availableForShifts) {
        this.availableForShifts = availableForShifts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("EmployeeCategory (");

        sb.append(categoryId);
        sb.append(", ").append(categoryName);
        sb.append(", ").append(admin);
        sb.append(", ").append(requiredPerShift);
        sb.append(", ").append(availableForShifts);

        sb.append(")");
        return sb.toString();
    }
}