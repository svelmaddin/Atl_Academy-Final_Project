package com.FinalProject.dto.studentdto;

import com.FinalProject.enums.Faculty;
import lombok.Data;

@Data
public class UpdateStudentDto {
    private Long id;
    private String name;
    private String surname;
    private Faculty faculty;
    private String studentFIN;
    private boolean deleteStatus;
}