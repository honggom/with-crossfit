// 쿠키가 없거나 토큰이 만료됐을 경우 동작
function errorHandle(error, navigate) {
    console.log(error);
    if (error.response.status === 401) {
        alert("로그인이 만료되었습니다.");
        navigate("/logout", { replace: true });
    } else if (error.response.status === 403) {
        alert("권한이 없습니다.");
        navigate("/logout", { replace: true });
    } else {
        alert("오류가 발생했습니다.");
        navigate("/logout", { replace: true });
    }
}
// value가 숫자면 ture를 리턴 아니면 false를 리턴
function isRightNumber(value) {
    const regex = /^[0-9]*$/;

    if (regex.test(value)) {
        return true;
    } else {
        return false;
    }
}

// kg를 lb로 환산
function calculateToLb(kg) {
    return Math.ceil(kg * 2.2046);
}

// lb를 kg로 환산
function calculateToKg(lb) {
    return Math.ceil(lb * 0.4535);
}

// number를 percent에 맞게 변환해줌 ex) percent : 90, number : 100 ==> return 90
function percentTranslate(percent, number) {
    return percent * number / 100;
}

// 시간을 오름차순으로 정렬한다
function sortByTime(arr) {
    const temp = [...arr];

    temp.sort((a, b) => {
        const aTimes = a.start.split(':');
        const bTimes = b.start.split(':');
        const aTime = Number(aTimes[0] + aTimes[1]);
        const bTime = Number(bTimes[0] + bTimes[1]);

        if (aTime > bTime) {
            return 1;
        }

        if (aTime < bTime) {
            return -1;
        }
        return 0;
    })
    return temp;
}

export { errorHandle, isRightNumber, calculateToLb, calculateToKg, percentTranslate, sortByTime };