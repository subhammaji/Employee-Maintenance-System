import axios from "axios";

const REST_API_BASE_URL = "http://localhost:8080/api/employees";

//get all the employees
export const listEmployees = () => axios.get(REST_API_BASE_URL);

//add the employee rest api call
export const createEmployee = (employee) => axios.post(REST_API_BASE_URL, employee);

//get employee by id rest api call
export const getEmployee = (employeeId) => axios.get(REST_API_BASE_URL + '/' + employeeId);

//  update employee rest api call
export const updateEmployee = (employeeId, employee) => axios.put(REST_API_BASE_URL + '/' + employeeId, employee);

//delete employee rest api call
export const deleteEmployee = (employeeId) => axios.delete(REST_API_BASE_URL + '/' + employeeId);