import axios from 'axios';
import './App.css';
import React, {useCallback, useEffect, useState, useRef} from 'react'
import {useDropzone} from 'react-dropzone'
import './addUserForm.css';


const UserProfiles = () =>{
  const [UserProfiles, setUserProfiles] = useState([]);
  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, [])
  return UserProfiles.map((userProfile, index) => {
    return (
      <div key = {index}>
        {userProfile.userProfileId ? (
          <img 
            src = {`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`} 
            alt='hey'
          />
        ) : null}
        <br/>
        <br/>
        <h1>{userProfile.userName}</h1>
        <p>{"UserID : " + userProfile.userProfileId}</p>
        <MyDropzone {...userProfile} />
        <br/>
      </div>
    );
  });
};

function MyDropzone({ userProfileId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
      formData,
      {
        headers : {
          "Content-Type" : "multipart/form-data"
        }
      }
    ).then(() => {
      console.log("file uploaded successfully")
    }).catch(err => {
      console.log(err);
    })
   }, []);
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  );
}

function AddUserForm({setShowForm}, {}) {
  const formRef = useRef(null);
  const [formData, setFormData] = useState({
    userId: '',
    userName: '',
    userImageLink: ''
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

    axios.post('http://localhost:8080/api/v1/user-profile/saveUserProfile', formData)
      .then(() => {
        console.log('User profile saved successfully');
        // Clear the form fields or update the user profiles
        console.log('User profile saved successfully');
        window.alert('User added successfully. Please reload the page');
        setFormData({
          // userId: '',
          userName: '',
          // userImageLink: ''
        });
        setShowForm(false);
      })
      .catch((err) => {
        console.error('Error saving user profile:', err);
      });
  };

  return (
    <div ref = {formRef} className="add-user-form">
      <h2>Add New User</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>User Name:</label>
          <input type="text" name="userName" value={formData.userName} onChange={handleChange} />
        </div>
        {/* <div>
          <label>User Image Link:</label>
          <input type="text" name="userImageLink" value={formData.userImageLink} onChange={handleChange} />
        </div> */}
        <div>
          <button type="button" onClick={() => setShowForm(false)}>Cancel</button>
          <button type="submit">Submit</button>
      </div>
      </form>
    </div>
  );  
}


function App() {
  const [showForm, setShowForm] = useState(false);
  const formRef = useRef(null);
  return (
    <div className="App">
      <UserProfiles />
      <button onClick={() => { 
        setShowForm(true); 
        formRef.current.scrollIntoView({ block: 'start', behavior: 'smooth', inline: 'nearest', offsetTop: -100 }); 
        }}
        style={{ fontSize: '16px', fontFamily: 'Arial', padding: '10px 20px', cursor: 'pointer', backgroundColor: '#0d0d0d', borderRadius:'25px', border :'0px', color : 'whitesmoke'}}
      >
         <b>Add New User</b>
      </button>
      {showForm && <AddUserForm setShowForm={setShowForm} />}
      <div ref={formRef}></div> 
    </div>
  );
}


export default App;
