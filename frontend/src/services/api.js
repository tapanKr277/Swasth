import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react';

export const api = createApi({
  reducerPath: 'api',
  baseQuery: fetchBaseQuery({ baseUrl: import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api' }),
  tagTypes: ['Patient', 'Treatment'],
  endpoints: () => ({}),
});
