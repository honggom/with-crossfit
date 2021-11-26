import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const insertSpecificSchedule = async (name, eachTimes, dateStr) => {

    const data = {
        name: name,
        times: eachTimes,
        dateStr: dateStr
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/admin/api/specific-schedule`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

const insertDayOffSpecificSchedule = async (dateStr) => {

    const data = {
        dateStr: dateStr
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/admin/api/specific-schedule/day-off`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { insertSpecificSchedule, insertDayOffSpecificSchedule };