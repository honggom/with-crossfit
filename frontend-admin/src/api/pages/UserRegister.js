import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getBox = async (id) => {

    const config = {
        method: 'GET',
        url: `${backEndBaseUri}/admin/api/box`,
        withCredentials: true,
    }

    return await axios(config);
};

export { getBox };