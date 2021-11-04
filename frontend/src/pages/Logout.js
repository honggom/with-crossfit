import React, { useEffect } from 'react';
import { useCookies } from 'react-cookie';

export default function Logout() {

    const [cookies, setCookie, removeCookie] = useCookies(['with-crossfit']);

    useEffect(() => {
        alert("로그아웃됨");
        removeCookie('refresh');
        removeCookie('jwt');
    })

    return (
        <div>
            aaa
        </div>
    );
}