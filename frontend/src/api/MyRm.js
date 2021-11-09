import axios from "axios";

const backEndBaseUri = process.env.REACT_APP_BACK_END_BASE_URI;

const getMyRm = async () => {

  const config = {
    method: 'GET',
    url: `${backEndBaseUri}/api/my-rm`,
    withCredentials: true,
  }

  return await axios(config);
};

const insertMyRm = async (MyRmDto) => {

  const data = {
    name: MyRmDto.name,
    repetition: MyRmDto.repetition,
    lb: MyRmDto.lb
  }

  const config = {
    method: 'POST',
    url: `${backEndBaseUri}/api/my-rm`,
    data: data,
    withCredentials: true,
  }

  return await axios(config);
};

export { getMyRm, insertMyRm };