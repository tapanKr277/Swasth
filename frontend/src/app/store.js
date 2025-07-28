// src/app/store.js
import { configureStore } from '@reduxjs/toolkit';
import rootReducer from './rootReducer';
import { apiSlice } from '../services/api';

const store = configureStore({
  reducer: {
    ...rootReducer,
    [apiSlice.reducerPath]: apiSlice.reducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware().concat(apiSlice.middleware),
});

export default store;
