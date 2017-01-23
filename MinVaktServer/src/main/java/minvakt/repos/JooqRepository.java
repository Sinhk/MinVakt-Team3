package minvakt.repos;

import minvakt.datamodel.AssignedEmployee;
import minvakt.datamodel.ShiftDetailed;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.jooq.Result;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.modelmapper.jooq.RecordValueReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static minvakt.datamodel.Tables.*;

@Component
public class JooqRepository {

    private final DSLContext create;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public JooqRepository(DSLContext dslContext) {
        this.create = dslContext;
        modelMapper.getConfiguration().addValueReader(new RecordValueReader());
        modelMapper.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
    }

    public List<ShiftDetailed> getShiftDetailed() {
        List<ShiftDetailed> shifts = new ArrayList<>();
        Map<Integer, Result<Record8<Integer, LocalDateTime, LocalDateTime, Integer, Boolean, Integer, String, String>>> resultMap = this.create
                .select(SHIFT.SHIFT_ID, SHIFT.TO_TIME, SHIFT.FROM_TIME, SHIFT.RESPONSIBLE_EMPLOYEE_ID, SHIFT_ASSIGNMENT.ABSENT,
                        EMPLOYEE.EMPLOYEE_ID, EMPLOYEE.FIRST_NAME, EMPLOYEE.LAST_NAME)
                .from(SHIFT).naturalJoin(SHIFT_ASSIGNMENT).naturalJoin(EMPLOYEE)
                .fetchGroups(SHIFT.SHIFT_ID);

        resultMap.values().forEach(record8s -> {
            ShiftDetailed shiftDetailed = modelMapper.map(record8s.get(0), ShiftDetailed.class);
            System.out.println(shiftDetailed);
            record8s.forEach(record8 -> {
                AssignedEmployee employee = modelMapper.map(record8, AssignedEmployee.class);
                shiftDetailed.addEmployee(employee);
            });
            shifts.add(shiftDetailed);
        });

        return shifts;
    }
}