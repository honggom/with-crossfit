import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getSpecificSchedule = async (start, end) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/specific-schedule/${start}/${end}`,
        withCredentials: true,
    }

    return await axios(config);
};

const getNow = async () => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/now`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getSpecificSchedule, getNow };