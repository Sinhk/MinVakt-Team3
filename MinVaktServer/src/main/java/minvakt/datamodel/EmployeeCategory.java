package minvakt.datamodel;

import javax.persistence.*;
import java.util.List;

/**
 * MinVakt-Team3
 * 15.01.2017
 */
@Entity
@Table(name = "employee_category", schema = "g_scrum03", catalog = "")
public class EmployeeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column(nullable = false)
    private String categoryName;
    private boolean admin;
    private int requiredPerShift;
    @OneToMany(mappedBy = "category")
    private List<Employee> employeesByCategory;

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public int getRequiredPerShift() {
        return requiredPerShift;
    }

    public List<Employee> getEmployeesByCategory() {
        return employeesByCategory;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public void setRequiredPerShift(int requiredPerShift) {
        this.requiredPerShift = requiredPerShift;
    }

    public void setEmployeesByCategory(List<Employee> employeesByCategory) {
        this.employeesByCategory = employeesByCategory;
    }

    @Override
    public String toString() {
        return "EmployeeCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", admin=" + admin +
                ", requiredPerShift=" + requiredPerShift +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeCategory that = (EmployeeCategory) o;

        if (categoryId != that.categoryId) return false;
        if (admin != that.admin) return false;
        if (!categoryName.equals(that.categoryName)) return false;
        return requiredPerShift == (that.requiredPerShift);
    }

    @Override
    public int hashCode() {
        int result = categoryId;
        result = 31 * result + categoryName.hashCode();
        result = 31 * result + (admin ? 1 : 0);
        result = 31 * result + requiredPerShift;
        return result;
    }
}
