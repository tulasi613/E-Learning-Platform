import React, { createContext, useState, useEffect } from 'react';

export const AuthContext = createContext();

const AuthProvider = ({ children }) => {
  const [auth, setAuth] = useState({
    token: localStorage.getItem('token'),
    role: null,
  });

  useEffect(() => {
    if (auth.token) {
      const decodedToken = JSON.parse(atob(auth.token.split('.')[1]));
      setAuth((prev) => ({ ...prev, role: decodedToken.roles[0] }));
    }
  }, [auth.token]);

  return (
    <AuthContext.Provider value={{ auth, setAuth }}>
      {children}
    </AuthContext.Provider>
  );
};

export default AuthProvider;