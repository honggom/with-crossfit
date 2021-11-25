import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getScheduleByBox = async () => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/schedule`,
        withCredentials: true,
    }

    return await axios(config);
};

const getScheduleSetByBox = async () => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/schedule-set`,
        withCredentials: true,
    }

    return await axios(config);
};


export { getScheduleByBox, getScheduleSetByBox };