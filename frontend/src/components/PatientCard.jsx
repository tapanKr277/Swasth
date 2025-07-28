// src/components/PatientCard.jsx
import React from 'react';

const PatientCard = ({ patient }) => {
  if (!patient) return <div>No patient data available.</div>;

  return (
    <div className="border rounded-lg shadow p-4 bg-white">
      <h2 className="text-xl font-semibold mb-2">Patient Information</h2>
      <p><strong>Name:</strong> {patient.name}</p>
      <p><strong>Age:</strong> {patient.age}</p>
      <p><strong>Gender:</strong> {patient.gender}</p>
      <p><strong>Contact:</strong> {patient.contact}</p>
      <p><strong>Patient ID:</strong> {patient.patientId}</p>
    </div>
  );
};

export default PatientCard;
