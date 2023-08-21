import React, { useState } from 'react';
import axios from 'axios';

function CreateUserForm({ fetchUserProfiles }) {
  const [formData, setFormData] = useState({
    userId: '',
    userName: '',
    userProfileLink: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    axios.post('http://localhost:8080/api/v1/user-profile/saveUser', formData)
      .then(() => {
        console.log('User profile saved successfully');
        console.log(formData)
        fetchUserProfiles(); // Refresh user profiles after adding a new user
      })
      .catch((err) => {
        console.error('Error saving user profile:', err);
      });
  };

  return (
    <div>
      <h2>Create New User Profile</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>User ID:</label>
          <input type="text" name="userId" value={formData.userId} onChange={handleChange} />
        </div>
        <div>
          <label>User Name:</label>
          <input type="text" name="userName" value={formData.userName} onChange={handleChange} />
        </div>
        <div>
          <label>User Profile Link:</label>
          <input type="text" name="userProfileLink" value={formData.userProfileLink} onChange={handleChange} />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
}

export default CreateUserForm;
