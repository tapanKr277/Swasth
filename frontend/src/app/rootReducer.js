// src/app/rootReducer.js
import { combineReducers } from '@reduxjs/toolkit';
import patientReducer from '../features/patient/patientSlice';
import treatmentReducer from '../features/treatment/treatmentSlice';
import authReducer from '../features/auth/authSlice';

const rootReducer = combineReducers({
  // patient: patientReducer,
  // treatment: treatmentReducer,
  auth: authReducer,
});

export default rootReducer;
