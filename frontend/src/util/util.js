// 쿠키가 없거나 토큰이 만료됐을 경우 동작
function ifExpired(response, navigate) {
    if (response.data.error === 403) {
        alert("로그인이 만료되었습니다.");
        navigate("/logout", { replace: true });
        throw new Error("로그인 만료됨");
    }
}

export { ifExpired };