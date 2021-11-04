import React, { useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { Navigate } from 'react-router-dom';

export default function Logout() {

    const [cookies, setCookie, removeCookie] = useCookies(['with-crossfit']);
    const jwtName = process.env.REACT_APP_JWT_NAME;
    const refreshName = process.env.REACT_APP_REFRESH_NAME;

    useEffect(() => {
        removeCookie(jwtName);
        removeCookie(refreshName);
    })

    return (
        <div>
           <Navigate to="/"/>
        </div>
    );
}