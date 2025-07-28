// src/hooks/useAuth.js
import { useSelector, useDispatch } from 'react-redux';
import { loginSuccess, logout, setAuthError, clearAuthError } from '../features/auth/authSlice';
import { authService } from '../services/authService';

export const useAuth = () => {
  const authState = useSelector((state) => state.auth);
  const dispatch = useDispatch();

  const login = async (credentials) => {
    dispatch(clearAuthError());
    try {
      const data = await authService.login(credentials);
      dispatch(loginSuccess(data));
      return { success: true };
    } catch (error) {
      dispatch(setAuthError(error.response?.data?.message || 'Login failed'));
      return { success: false, error: error.message };
    }
  };

  const handleLogout = async () => {
    await authService.logout();
    dispatch(logout());
  };

  return {
    ...authState,
    login,
    logout: handleLogout,
  };
};
