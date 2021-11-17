import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getWod = async (size, page, sort) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/wod?size=${size}&page=${page}&sort=${sort}`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getWod };