import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const insertSchedule = async (name, eachTimes) => {

    const data = {
        name: name,
        times: eachTimes,
    }

    const config = {
        method: 'POST',
        url: `${backEndBaseUri}/admin/api/schedule`,
        data: data,
        withCredentials: true,
    }

    return await axios(config);
};

export { insertSchedule };