import React, { useState , useEffect} from 'react'
import { createEmployee } from '../services/EmployeeService'
import { getEmployee } from '../services/EmployeeService'
import { updateEmployee } from '../services/EmployeeService'
import { useNavigate, useParams } from 'react-router-dom'

const EmployeeComponent = () => {

    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [project, setProject] = useState('');
    const [salary, setSalary] = useState('');

    const [errors, setErrors] = useState({
        'firstName': '',
        'lastName': '',
        'email': '',
        'salary': '',
        'project': ''
    })

    const navigator = useNavigate('');

    const {id} = useParams();

    useEffect(() => {
        if(id) {
            //fetch employee by id and set the state variable
            getEmployee(id).then((response) => {
                setFirstName(response.data.firstName);
                setLastName(response.data.lastName);
                setEmail(response.data.email);
                setProject(response.data.project);
                setSalary(response.data.salary);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [id])

    function saveOrUpdateEmployee(e) {
        e.preventDefault();
        
        if(validateForm()) {
            const employee = {firstName, lastName, email, project, salary};
            console.log(employee);
            
            if(id) {
                //update employee
                updateEmployee(id, employee).then((response) => {
                    console.log(response.data);
                    navigator('/employees');
                }).catch(error => {
                    console.error(error);
                })
            } else {
                //create employee
                createEmployee(employee).then((response) => {
                    console.log(response.data);
                    navigator('/employees');
                }).catch(error => {
                    console.error(error);
                })
            }
        }
    }


    function validateForm() {
        let valid = true;
        const errorsCopy = {...errors}

        if(firstName.trim()) {
            errorsCopy.firstName = '';
        }else {
            errorsCopy.firstName = 'First Name required';
            valid = false;
        }

        if(lastName.trim()) {
            errorsCopy.lastName = '';
        }else {
            errorsCopy.lastName = 'Last Name required';
            valid = false;
        }

        if(email.trim()) {
            errorsCopy.email = '';
        }else {
            errorsCopy.email = 'Email required';
            valid = false;
        }
        setErrors(errorsCopy);
        return valid;
    }

    //if employeeid is present in the url then update employee else create
    function pageTitle(id) {
        if(id) {
            return <h2 className='text-center'>Update Employee</h2>
        }else {
            return <h2 className='text-center'>Add Employee</h2>
        }
    }


  return (
    <div className='container'>
        <br/>
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                {pageTitle(id)}
                <div className='card-body'>
                    <form>
                        <div className="form-group mb-2">
                            <label className='form-label'>First Name :</label>
                            <input 
                                type="text" 
                                placeholder='First Name' 
                                name='firstName' 
                                value={firstName}   //state-variable = firstName and below all onchange will set/update the state variable
                                className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                                onChange={e => setFirstName(e.target.value)}
                            />
                            {
                                errors.firstName && <div className='invalid-feedback'>{errors.firstName}</div>
                            }
                        </div>
                        
                        <div className="form-group mb-2">
                            <label className='form-label'>Last Name :</label>
                            <input 
                                type="text"
                                placeholder='Last Name' 
                                name='lastName' 
                                value={lastName}
                                className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                                onChange={e => setLastName(e.target.value)}
                                />
                                {
                                    errors.lastName && <div className='invalid-feedback'>{errors.lastName}</div>
                                }
                        </div>
                        
                        <div className="form-group mb-2">
                            <label className='form-label'>Email :</label>
                            <input 
                                type="email"
                                placeholder='Email' 
                                name='email' 
                                value={email}
                                className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                                onChange={e => setEmail(e.target.value)}
                                />
                                {
                                    errors.email && <div className='invalid-feedback'>{errors.email}</div>
                                }
                        </div>
                        
                        <div className="form-group mb-2">
                            <label className='form-label'>Project :</label>
                            <input 
                                type="text"
                                placeholder='Project' 
                                name='project' 
                                value={project}
                                className='form-control'
                                onChange={e => setProject(e.target.value)}
                            />
                        </div>
                        
                        <div className="form-group mb-2">
                            <label className='form-label'>Salary :</label>
                            <input
                                type="number"
                                placeholder='salary'
                                name='salary' 
                                value={salary}
                                className='form-control'
                                onChange={e => setSalary(e.target.value)}
                            />
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateEmployee}>Submit</button>
                    </form>
                </div>
            </div>
        </div>
        
    </div>
  )
}

export default EmployeeComponent