SET SERVEROUTPUT ON;
-- DBMS_OUTPUT.PUT_LINE();

-- Bai 1
-- Cau 1.1: dept_info(department_id)
CREATE OR REPLACE PROCEDURE dept_info(
    dept_id_param   IN departments.department_id%TYPE
)
AS
    dept_name_var       departments.department_name%TYPE;
    dept_manager_var    employees.first_name%TYPE;
    dept_city_var       locations.city%TYPE;
BEGIN
    SELECT department_name, (first_name||' '||last_name) , city
    INTO dept_name_var, dept_manager_var, dept_city_var
    FROM departments d
        LEFT JOIN employees e ON(d.manager_id = e.employee_id)
        JOIN locations l USING(location_id)
    WHERE d.department_id = dept_id_param;
    
    DBMS_OUTPUT.PUT_LINE('Department: ID= '||dept_id_param||
                        ', department name= '||dept_name_var||
                        ', manager= '||dept_manager_var||
                        ', city= '||dept_city_var);
EXCEPTION
    WHEN NO_DATA_FOUND THEN DBMS_OUTPUT.PUT_LINE('No data found with department id: '||dept_id_param);
    WHEN OTHERS THEN DBMS_OUTPUT.PUT_LINE('Unknown error has occured');
END;     
/

-- test
BEGIN
    dept_info(120);
END;
/

--Cau 1.2
CREATE OR REPLACE PROCEDURE add_job(
    job_id_param        jobs.job_id%TYPE,
    job_title_param     jobs.job_title%TYPE
)
AS
BEGIN
    INSERT INTO jobs
        VALUES (job_id_param, job_title_param, null, null);
END;
/

--test
BEGIN
    add_job('TID', 'Title Job test');
END;
/

-- Cau 1.3
CREATE OR REPLACE PROCEDURE update_comm(
    emp_id_param        employees.employee_id%TYPE
)
AS
BEGIN
    UPDATE employees
    SET commission_pct = commission_pct * 1.05
    WHERE employee_id = emp_id_param;
END;
/

-- test
BEGIN 
    update_comm(300);
END;
/

-- Cau 1.4
CREATE OR REPLACE PROCEDURE add_emp(
    first_name_param        employees.first_name%TYPE,
    last_name_param         employees.last_name%TYPE,
    email_param             employees.email%TYPE,
    phone_number_param      employees.phone_number%TYPE,
    hire_date_param         employees.hire_date%TYPE,
    job_id_param            employees.job_id%TYPE,
    salary_param            employees.salary%TYPE,
    comission_pct_param     employees.commission_pct%TYPE DEFAULT NULL,
    manager_id_param        employees.manager_id%TYPE DEFAULT NULL,
    department_id           employees.department_id%TYPE
)
AS
    emp_id_var              employees.employee_id%TYPE;
BEGIN
    SELECT employees_seq.NEXTVAL INTO emp_id_var from dual;
    
    INSERT INTO employees
        VALUES (
            emp_id_var,
            first_name_param,
            last_name_param,
            email_param,
            phone_number_param,
            hire_date_param,
            job_id_param,
            salary_param,
            comission_pct_param,
            manager_id_param,
            department_id
            );
END;
/
-- test
BEGIN
    add_emp('fn test',
            'ln test',
            'email test',
            'phone test',
            TO_DATE('12-12-2022','dd-mm-yyyy'),
            'HR_REP',
            12347,
            0.2,
            100,
            90);
END;
/

SET SERVEROUTPUT ON;


-- Cau 1.5
CREATE OR REPLACE PROCEDURE delete_emp(
    emp_id_param        employees.employee_id%TYPE
)
AS
    no_data_delete      EXCEPTION;
BEGIN
    DELETE FROM employees WHERE employee_id = emp_id_param; 
    IF SQL%ROWCOUNT = 0 THEN RAISE no_data_delete;
    END IF;
EXCEPTION
    WHEN no_data_delete THEN dbms_output.put_line('No such record');
END;
/
-- test
BEGIN
   delete_emp(300); 
END;
/

-- Cau 1.6
CREATE OR REPLACE PROCEDURE find_emp
AS
    CURSOR find_emp_cusor IS
        SELECT employee_id, (first_name||' '||last_name) as Name,
                salary, job_title
        FROM employees e
            JOIN jobs j ON (e.job_id = j.job_id)
        WHERE e.salary > j.min_salary AND e.salary < j.max_salary
        ORDER BY 4, 3;
    found_emp_row        find_emp_cusor%ROWTYPE;
BEGIN
    OPEN find_emp_cusor;
    LOOP 
        FETCH find_emp_cusor INTO found_emp_row;
        EXIT WHEN find_emp_cusor%NOTFOUND;
        
        dbms_output.put_line('Employee infomation: '||
                            ', ID = '||found_emp_row.employee_id||
                            ', name = '||found_emp_row.name||
                            ', salary = '||found_emp_row.salary||
                            ', job title = '||found_emp_row.job_title);
    END LOOP;
    CLOSE find_emp_cusor;    
END;
/

-- test
BEGIN 
    find_emp;
END;
/
SELECT employee_id, (first_name||' '||last_name) as Name,
                salary, job_title
        FROM employees e
            JOIN jobs j ON (e.job_id = j.job_id)
        WHERE e.salary > j.min_salary AND e.salary < j.max_salary;

-- Cau 1.7
CREATE OR REPLACE PROCEDURE update_comm
IS
    sql_var         VARCHAR2(400);
BEGIN
    sql_var := 'UPDATE employees SET salary = CASE ' ||
        'WHEN MONTHS_BETWEEN(sysdate, hire_date) > 2 * 12 THEN salary + 200 '||
        'WHEN MONTHS_BETWEEN(sysdate, hire_date) > 1 * 12 AND  MONTHS_BETWEEN(sysdate, hire_date) < 2 * 12 THEN salary + 100 '||
        'WHEN CEIL(MONTHS_BETWEEN(sysdate, hire_date)) = 1 * 12 THEN salary + 50 END';
    EXECUTE IMMEDIATE sql_var;
END;
/

--test
BEGIN
    update_comm;
END;
/

-- Cau 1.8
CREATE OR REPLACE PROCEDURE job_his(
    emp_id_param        employees.employee_id%TYPE
)
AS
    CURSOR emp_cursor(emp_id_param   employees.employee_id%TYPE) IS
        SELECT (first_name||' '||last_name) as name,
                start_date,
                end_date,
                job_title
        FROM job_history jh
            JOIN employees e ON (jh.employee_id = e.employee_id)
            JOIN jobs j ON (j.job_id = jh.job_id)
        WHERE jh.employee_id = emp_id_param
        ORDER BY 2;
    
    job_his_row_var     emp_cursor%ROWTYPE;
    no_job_his_found    EXCEPTION;
BEGIN
    OPEN emp_cursor(emp_id_param);
    IF emp_cursor%ROWCOUNT = 0 
        THEN RAISE no_job_his_found;
    END IF;
    LOOP
        FETCH emp_cursor INTO job_his_row_var;
        EXIT WHEN emp_cursor%NOTFOUND;
        DBMS_OUTPUT.PUT_LINE('Job history: '||
                                'ID = '||emp_id_param||
                                ', name = '||job_his_row_var.name||
                                ', job title = '||job_his_row_var.job_title||
                                ', start date = '||TO_CHAR(job_his_row_var.start_date)||
                                ', end date = '||TO_CHAR(job_his_row_var.end_date));
    END LOOP;
    CLOSE emp_cursor;
EXCEPTION 
    WHEN no_job_his_found THEN DBMS_OUTPUT.PUT_LINE('No job history found for employee id = '||emp_id_param);
END;
/

--test
BEGIN
    job_his(102);
END;
/

SELECT (e.first_name||' '||e.last_name) AS name,
                jh.start_date,
                jh.end_date,
                j.job_title
        FROM job_history jh
            JOIN employees e ON (jh.employee_id = e.employee_id)
            JOIN jobs j ON (j.job_id = jh.job_id)
        WHERE jh.employee_id = 101
        ORDER BY 2;


-- Bai 2:
-- Cau 2.1:
CREATE OR REPLACE FUNCTION sum_salary(
    dept_id_param departments.department_id%TYPE
)
RETURN NUMBER
AS
    sum_salary_var NUMBER;
BEGIN
    SELECT SUM(salary)
    INTO sum_salary_var
    FROM departments
        JOIN employees USING(department_id)
    WHERE department_id = dept_id_param;
    RETURN sum_salary_var;

END;
/

--test
SELECT department_name, sum_salary(130) AS sum_salary
FROM departments d
WHERE d.department_id = 130;

-- Cau 2.2  
CREATE OR REPLACE FUNCTION name_con(
    country_id_param        countries.country_id%TYPE
)
RETURN countries.country_name%TYPE
AS
    c_name_var      countries.country_name%TYPE;
BEGIN
    SELECT country_name INTO c_name_var
    FROM countries
    WHERE country_id = country_id_param;
    
    RETURN c_name_var;
END;
/

--test
SELECT name_con('ARC') FROM dual;


-- Cau 2.3
CREATE OR REPLACE FUNCTION annual_comp(
    salary_param            NUMBER,
    comission_pct_param     NUMBER
)
RETURN NUMBER
AS
    salary_year_var         NUMBER;
BEGIN
    salary_year_var := salary_param*12 +(salary_param * comission_pct_param * 12);
    
    RETURN salary_year_var;
END;
/
--test
SELECT annual_comp(1000, 0.05) FROM dual;

-- Cau 2.4
CREATE OR REPLACE FUNCTION avg_salary(
    dept_id_param       departments.department_id%TYPE 
)
RETURN NUMBER
AS
    avg_sal_var         NUMBER;
BEGIN
    SELECT TRUNC(AVG(salary), 2)
    INTO avg_sal_var
    FROM employees 
    WHERE department_id = dept_id_param;
    
    RETURN avg_sal_var;
END;
/

-- test
select AVG_SALARY(90) FROM dual;

-- Cau 2.5
CREATE OR REPLACE FUNCTION time_work(
    emp_id_param        employees.employee_id%TYPE
)
RETURN NUMBER
AS
    emp_timework_var      NUMBER;
BEGIN
    SELECT CEIL(MONTHS_BETWEEN(sysdate, hire_date))
    INTO emp_timework_var
    FROM employees e
    WHERE employee_id = emp_id_param; 
    
    RETURN emp_timework_var;
END;
/

--test
SELECT (first_name || ' '|| last_name) AS Employee
        , time_work(112) AS "TIME_WORK(MONTH)"
    FROM employees e
    WHERE e.employee_id = 112;



-- Bai 3
-- Cau 3.1
CREATE OR REPLACE TRIGGER hiring_day_trigger
BEFORE INSERT OR UPDATE OF hire_date
ON employees
FOR EACH ROW
WHEN (NEW.hire_date > sysdate)
BEGIN
    RAISE_APPLICATION_ERROR(-20031, 'Hire day must be by or today');
END;
/
--test
INSERT INTO employees (employee_id, last_name, email, hire_date, job_id)
    VALUES (207, 'Last Name test', 'email test', TO_DATE('01/12/2023', 'dd/mm/yyyy'), 'HR_REP');

-- Cau 3.2
CREATE OR REPLACE TRIGGER min_max_salary_trigger
BEFORE INSERT OR UPDATE OF min_salary, max_salary
ON jobs
FOR EACH ROW
WHEN (NEW.min_salary >= NEW.max_salary)
BEGIN
    RAISE_APPLICATION_ERROR(-20032, 'Min salary must less than max salary');
END;
/
--test
INSERT INTO jobs
    VALUES('TTT', 'Min max trigger test', 5000, 4000);
    
-- Cau 3.3
CREATE OR REPLACE TRIGGER job_his_trigger
BEFORE INSERT OR UPDATE OF start_date, end_date
ON job_history
FOR EACH ROW
WHEN (NEW.start_date > NEW.end_date)
BEGIN
    RAISE_APPLICATION_ERROR(-20033, 'Start date must be before end date');
END;
/
-- test
INSERT INTO job_history
    VALUES (107, TO_DATE('07/02/2007', 'dd/mm/yyyy'), TO_DATE('07/02/2007', 'dd/mm/yyyy'), 'IT_PROG', 60);
    
    
-- Cau 3.4
CREATE OR REPLACE TRIGGER commission_update_trigger
BEFORE UPDATE OF commission_pct
ON employees
FOR EACH ROW
WHEN (NEW.commission_pct < OLD.commission_pct)
BEGIN
    RAISE_APPLICATION_ERROR(-20034, 'Commission percent can only increase');
END;
/

--test
UPDATE employees
    SET commission_pct = 0.4
    WHERE employee_id = 145;

